let currentProduct = null;
let paymentMethods = [];
let orderContext = null;
let allProducts = [];
let filteredProducts = [];
let currentCategoryId = null;
let currentPage = 0;
const PAGE_SIZE = 28;

document.addEventListener("DOMContentLoaded", async () => {
    if (!isAuthenticated()) {
        redirectLogin();
        return;
    }
    updateCartBadge();
    document.querySelector(".filter-input").addEventListener("input", applyFilters);
    await Promise.all([loadPaymentMethods(), loadCategories(), mountProductsGrid()]);
});

async function mountProductsGrid() {
    allProducts = await getProductsData();
    applyFilters();
}

function renderProducts(products) {
    const gridContainer = document.getElementById("products-grid");
    gridContainer.innerHTML = "";

    products.forEach(product => {
        const card = document.createElement("div");
        card.classList.add("product-card");
        card.innerHTML = `
            <img src="${product.imageUrl || 'images/bag.png'}" alt="${product.name}" class="product-img">
            <div class="product-description">
                <div class="product-title">
                    <p>${product.name}</p>
                    <p>R$ ${product.price.toFixed(2)}</p>
                </div>
                <span class="availability-badge ${product.availability ? 'badge-available' : 'badge-unavailable'}">
                    ${product.availability ? "Dispon\u00EDvel" : "Indispon\u00EDvel"}
                </span>
            </div>
        `;
        card.addEventListener("click", () => openProductModal(product));
        gridContainer.appendChild(card);
    });
}

function applyFilters() {
    const searchText = document.querySelector(".filter-input").value.toLowerCase().trim();
    let filtered = allProducts;

    if (currentCategoryId !== null) {
        filtered = filtered.filter(p => p.categoryId === currentCategoryId);
    }

    if (searchText) {
        filtered = filtered.filter(p => p.name.toLowerCase().includes(searchText));
    }

    filteredProducts = filtered;
    currentPage = 0;
    renderCurrentPage();
}

function renderCurrentPage() {
    const start = currentPage * PAGE_SIZE;
    const end = start + PAGE_SIZE;
    renderProducts(filteredProducts.slice(start, end));
    updatePagination();
}

function updatePagination() {
    const totalPages = Math.ceil(filteredProducts.length / PAGE_SIZE);
    const container = document.getElementById("pagination");
    container.innerHTML = "";

    if (totalPages <= 1) return;

    const prev = document.createElement("button");
    prev.type = "button";
    prev.classList.add("page-btn");
    prev.textContent = "\u2190";
    prev.disabled = currentPage === 0;
    prev.addEventListener("click", () => { currentPage--; renderCurrentPage(); });
    container.appendChild(prev);

    for (let i = 0; i < totalPages; i++) {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.classList.add("page-btn");
        if (i === currentPage) btn.classList.add("page-btn-active");
        btn.textContent = i + 1;
        btn.addEventListener("click", () => { currentPage = i; renderCurrentPage(); });
        container.appendChild(btn);
    }

    const next = document.createElement("button");
    next.type = "button";
    next.classList.add("page-btn");
    next.textContent = "\u2192";
    next.disabled = currentPage === totalPages - 1;
    next.addEventListener("click", () => { currentPage++; renderCurrentPage(); });
    container.appendChild(next);
}

async function getProductsData() {
    try {
        const response = await fetch("http://localhost:8080/products", {
            headers: { Authorization: `Bearer ${getToken()}` }
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    } catch (error) {
        console.error("Fetch error:", error);
        return [];
    }
}

async function loadPaymentMethods() {
    try {
        const response = await fetch("http://localhost:8080/payment-methods", {
            headers: { Authorization: `Bearer ${getToken()}` }
        });
        if (!response.ok) throw new Error();
        paymentMethods = await response.json();
    } catch {
        paymentMethods = [];
    }
}

async function loadCategories() {
    try {
        const response = await fetch("http://localhost:8080/categories", {
            headers: { Authorization: `Bearer ${getToken()}` }
        });
        if (!response.ok) throw new Error();
        const categories = await response.json();
        const list = document.getElementById("categories-list");
        list.innerHTML = "";

        const allBtn = document.createElement("button");
        allBtn.type = "button";
        allBtn.classList.add("filter-btn", "active");
        allBtn.textContent = "Todos";
        allBtn.addEventListener("click", () => {
            currentCategoryId = null;
            list.querySelectorAll(".filter-btn").forEach(b => b.classList.remove("active"));
            allBtn.classList.add("active");
            applyFilters();
        });
        list.appendChild(allBtn);

        categories.forEach(cat => {
            const btn = document.createElement("button");
            btn.type = "button";
            btn.classList.add("filter-btn");
            btn.textContent = cat.title;
            btn.addEventListener("click", () => {
                currentCategoryId = cat.id;
                list.querySelectorAll(".filter-btn").forEach(b => b.classList.remove("active"));
                btn.classList.add("active");
                applyFilters();
            });
            list.appendChild(btn);
        });
    } catch {
        // fail silently
    }
}

function openProductModal(product) {
    currentProduct = product;
    document.getElementById("modal-name").textContent = product.name;
    document.getElementById("modal-price").textContent = `R$ ${product.price.toFixed(2)}`;
    document.getElementById("modal-availability").textContent = product.availability ? "Dispon\u00EDvel" : "Indispon\u00EDvel";
    document.getElementById("modal-img").src = product.imageUrl || "images/bag.png";
    document.getElementById("modal-actions").style.display = product.availability ? "" : "none";
    document.getElementById("product-modal").classList.add("open");
}

function closeProductModal() {
    document.getElementById("product-modal").classList.remove("open");
    currentProduct = null;
}

function addCurrentToCart() {
    if (currentProduct) {
        addToCart(currentProduct);
        closeProductModal();
    }
}

function buyNow() {
    if (!currentProduct) return;
    orderContext = "single";
    document.getElementById("product-modal").classList.remove("open");
    openPaymentModal();
}

function toggleCart() {
    const sidebar = document.getElementById("cart-sidebar");
    const overlay = document.getElementById("cart-overlay");
    const isOpen = sidebar.classList.contains("open");

    if (isOpen) {
        sidebar.classList.remove("open");
        overlay.classList.remove("open");
    } else {
        renderCartItems();
        sidebar.classList.add("open");
        overlay.classList.add("open");
    }
}

function openPaymentFromCart() {
    if (getCart().length === 0) {
        alert("O carrinho est\u00E1 vazio.");
        return;
    }
    orderContext = "cart";
    toggleCart();
    openPaymentModal();
}

function openPaymentModal() {
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
    currentProduct = null;
    orderContext = null;
}

async function confirmOrder() {
    const selected = document.querySelector('input[name="payment"]:checked');
    if (!selected) {
        alert("Selecione uma forma de pagamento.");
        return;
    }

    const paymentMethodId = parseInt(selected.value);
    const userId = getUserId();

    try {
        if (orderContext === "single" && currentProduct) {
            await createOrder(currentProduct.id, userId, paymentMethodId);
        } else if (orderContext === "cart") {
            for (const item of getCart()) {
                await createOrder(item.id, userId, paymentMethodId);
            }
            clearCart();
        }

        closePaymentModal();
        alert("Pedido realizado com sucesso!");
    } catch (error) {
        alert("Erro ao realizar pedido: " + error.message);
    }
}

async function createOrder(productId, userId, paymentMethodId) {
    const response = await fetch("http://localhost:8080/orders/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getToken()}`
        },
        body: JSON.stringify({ productId, userId, paymentMethodId })
    });

    if (!response.ok) {
        const msg = await response.text();
        throw new Error(msg);
    }
}
