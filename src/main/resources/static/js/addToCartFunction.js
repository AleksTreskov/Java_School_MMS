function addMore(id) {
    const quantity = document.getElementById('quantity' + id);
    if (quantity.textContent < 10)
        quantity.textContent = ++quantity.textContent;
}

function addLess(id) {
    const quantity = document.getElementById('quantity' + id);
    if (quantity.textContent > 1)
        quantity.textContent = --quantity.textContent;
}

async function addToCart(id) {
    let quantity = document.getElementById('quantity' + id);
    let addItem = {
        id: id,
        quantity: quantity.textContent
    };
    let cartCount = document.getElementById('cartCount');
    cartCount.innerText = parseInt(quantity.textContent) + parseInt(cartCount.innerText);
    quantity.textContent = 1;
    let call = await fetch("/addToCart", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(addItem)
    });
    await call.json();

}


