const API_BASE = "http://localhost:8080";
let pendingPayOrderId = null;
let paymentMethods = [];

document.addEventListener("DOMContentLoaded", async () => {
    if (!isAuthenticated()) {
        redirectLogin();
        return;
    }
    await Promise.all([loadPaymentMethods(), loadOrders()]);
});

async function loadOrders() {
    const userId = getUserId();
    try {
        const response = await fetch(`${API_BASE}/orders/user/${userId}`, {
            headers: { Authorization: `Bearer ${getToken()}` }
        });
        if (!response.ok) throw new Error();
        const orders = await response.json();
        renderOrders(orders);
    } catch {
        renderOrders([]);
    }
}

async function loadPaymentMethods() {
    try {
        const response = await fetch(`${API_BASE}/payment-methods`, {
            headers: { Authorization: `Bearer ${getToken()}` }
        });
        if (!response.ok) throw new Error();
        paymentMethods = await response.json();
    } catch {
        paymentMethods = [];
    }
}

function renderOrders(orders) {
    const colInProgress = document.getElementById("col-in-progress");
    const colCompleted = document.getElementById("col-completed");
    const colCancelled = document.getElementById("col-cancelled");

    colInProgress.innerHTML = "";
    colCompleted.innerHTML = "";
    colCancelled.innerHTML = "";

    const inProgress = orders.filter(o => o.state === "awaiting payment" || o.state === "payment_ok");
    const completed = orders.filter(o => o.state === "completed");
    const cancelled = orders.filter(o => o.state === "cancelled");

    document.getElementById("count-in-progress").textContent = inProgress.length;
    document.getElementById("count-completed").textContent = completed.length;
    document.getElementById("count-cancelled").textContent = cancelled.length;

    inProgress.forEach(order => colInProgress.appendChild(buildOrderCard(order)));
    completed.forEach(order => colCompleted.appendChild(buildOrderCard(order)));
    cancelled.forEach(order => colCancelled.appendChild(buildOrderCard(order)));

    if (inProgress.length === 0) colInProgress.appendChild(emptyState());
    if (completed.length === 0) colCompleted.appendChild(emptyState());
    if (cancelled.length === 0) colCancelled.appendChild(emptyState());
}

function buildOrderCard(order) {
    const stateLabels = {
        "awaiting payment": "Aguardando pagamento",
        "payment_ok": "Pagamento confirmado",
        "completed": "Finalizado",
        "cancelled": "Cancelado"
    };

    const stateClasses = {
        "awaiting payment": "state-awaiting",
        "payment_ok": "state-paid",
        "completed": "state-completed",
        "cancelled": "state-cancelled"
    };

    const card = document.createElement("div");
    card.classList.add("order-card");

    card.innerHTML = `
        <div class="order-card-header">
            <span class="order-id">#${order.id}</span>
            <span class="order-state ${stateClasses[order.state] || ""}">${stateLabels[order.state] || order.state}</span>
        </div>
        <div class="order-product-name">${order.productName}</div>
        <div class="order-details">
            <span class="order-price">R$ ${order.productPrice.toFixed(2)}</span>
            <span class="order-payment">${order.paymentMethodTitle}</span>
        </div>
        <div class="order-date">${order.creationDate}</div>
        ${order.state === "awaiting payment" ? `<button class="pay-btn" onclick="openPaymentModal(${order.id})">Realizar pagamento</button>` : ""}
    `;

    return card;
}

function emptyState() {
    const el = document.createElement("p");
    el.classList.add("column-empty");
    el.textContent = "Nenhum pedido";
    return el;
}

function openPaymentModal(orderId) {
    pendingPayOrderId = orderId;
    const list = document.getElementById("payment-methods-list");
    list.innerHTML = "";

    paymentMethods.forEach((pm, i) => {
        const label = document.createElement("label");
        label.classList.add("payment-option");
        label.innerHTML = `
            <input type="radio" name="payment" value="${pm.id}" ${i === 0 ? "checked" : ""}>
            ${pm.title}
        `;
        list.appendChild(label);
    });

    document.getElementById("payment-modal").classList.add("open");
}

function closePaymentModal() {
    document.getElementById("payment-modal").classList.remove("open");
    pendingPayOrderId = null;
}

async function confirmPayment() {
    if (!pendingPayOrderId) return;

    try {
        const response = await fetch(`${API_BASE}/orders/${pendingPayOrderId}/pay`, {
            method: "PATCH",
            headers: { Authorization: `Bearer ${getToken()}` }
        });

        if (!response.ok) {
            const msg = await response.text();
            alert("Erro: " + msg);
            return;
        }

        closePaymentModal();
        await loadOrders();
    } catch {
        alert("Erro ao processar pagamento.");
    }
}
