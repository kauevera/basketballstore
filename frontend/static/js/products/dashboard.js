
document.addEventListener("DOMContentLoaded", () => {
    mountProductsGrid();
});

async function mountProductsGrid() {
    const data = await getProductsData();
    const gridContainer = document.getElementById('products-grid');
    gridContainer.innerHTML = '';

    data.forEach(element => {
        const card = document.createElement('div');
        card.classList.add('product-card');

        card.innerHTML = `
            <img src="images/bag.png" alt="${element.name}" class="product-img">
            <div class="product-description">
                <div id="product-title">
                    <p>${element.name}</p>
                    <p>${element.price} R$</p>
                </div>
                <div id="product-availability">
                    <p>${element.availability ? "Disponível" : "Indisponível"}</p>
                </div>
            </div>
        `;

        gridContainer.appendChild(card);
    });
}

async function getProductsData() {
    console.log("entrou here");
    try {
        const response = await fetch('http://localhost:8080/products');
        
        if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Fetch error:', error);
        return []
    }
}