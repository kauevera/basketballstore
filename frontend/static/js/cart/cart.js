const CART_KEY = "cart";

function getCart() {
    const raw = localStorage.getItem(CART_KEY);
    return raw ? JSON.parse(raw) : [];
}

function saveCart(items) {
    localStorage.setItem(CART_KEY, JSON.stringify(items));
}

function addToCart(product) {
    const items = getCart();
    const existing = items.find(i => i.id === product.id);
    if (existing) {
        existing.qty += 1;
    } else {
        items.push({ id: product.id, name: product.name, price: product.price, qty: 1 });
    }
    saveCart(items);
    updateCartBadge();
}

function removeFromCart(productId) {
    saveCart(getCart().filter(i => i.id !== productId));
    renderCartItems();
    updateCartBadge();
}

function clearCart() {
    localStorage.removeItem(CART_KEY);
    renderCartItems();
    updateCartBadge();
}

function getCartCount() {
    return getCart().reduce((sum, i) => sum + i.qty, 0);
}

function getCartTotal() {
    return getCart().reduce((sum, i) => sum + i.price * i.qty, 0);
}

function updateCartBadge() {
    const badge = document.getElementById("cart-badge");
    if (!badge) return;
    const count = getCartCount();
    badge.textContent = count;
    badge.style.display = count > 0 ? "inline-flex" : "none";
}

function renderCartItems() {
    const container = document.getElementById("cart-items");
    const totalEl = document.getElementById("cart-total");
    if (!container) return;

    const items = getCart();
    container.innerHTML = "";

    if (items.length === 0) {
        container.innerHTML = '<p class="cart-empty">Seu carrinho est\u00E1 vazio.</p>';
        if (totalEl) totalEl.textContent = "";
        return;
    }

    items.forEach(item => {
        const el = document.createElement("div");
        el.classList.add("cart-item");
        el.innerHTML = `
            <div class="cart-item-info">
                <span class="cart-item-name">${item.name}</span>
                <span class="cart-item-qty">x${item.qty}</span>
            </div>
            <div class="cart-item-right">
                <span class="cart-item-price">R$ ${(item.price * item.qty).toFixed(2)}</span>
                <button class="cart-item-remove" onclick="removeFromCart(${item.id})">\u2715</button>
            </div>
        `;
        container.appendChild(el);
    });

    if (totalEl) {
        totalEl.textContent = `Total: R$ ${getCartTotal().toFixed(2)}`;
    }
}
