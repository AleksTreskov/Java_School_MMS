toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-full-width",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "2000",
    "extendedTimeOut": "2000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

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

function addToCart(id) {
    let quantity = document.getElementById('quantity' + id);
    let addItem = {
        productId: id,
        quantity: quantity.textContent
    };
    let cartCount = document.getElementById('cartCount');

    $.ajax({
        sync: true,
        method: 'POST',

        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/addToCart',
        data: JSON.stringify(addItem)
    }).done(function (result) {
        if (!result.error) {
            cartCount.innerText = parseInt(quantity.textContent) + parseInt(cartCount.innerText);
            quantity.textContent = 1;
            toastr.success("Added to cart");
        } else toastr.error(result.message)
    });


}


