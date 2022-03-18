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
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}
function showDivs(cartDiv, deliveryDiv, chosenAddressDiv, paymentDiv, finalDiv) {
    let cart = document.getElementById("cartDiv");
    let delivery = document.getElementById("deliveryDiv");
    let payment = document.getElementById("paymentDiv");
    let final = document.getElementById("finalDiv");
    let chosen = document.getElementById("addressChosenDiv");

    cart.style.display = cartDiv;
    delivery.style.display = deliveryDiv;
    payment.style.display = paymentDiv;
    chosen.style.display = chosenAddressDiv;
    final.style.display = finalDiv;
}

function chooseAddress(addressId) {
    showDivs('block', 'none', 'block', 'none', 'none');
    document.getElementById("hiddenAddressIdForDto").value = addressId;
    let chosenTable = document.getElementById("chosenAddressTable");
    for (let i = 1; i < chosenTable.rows.length; i++) {
        chosenTable.deleteRow(i);
    }
    let chosenRow = document.getElementById("rowAd" + addressId);
    let copy = chosenRow.cloneNode(true);
    chosenTable.appendChild(copy);
    chosenTable.rows[1].deleteCell(6);
}

function toDelivery() {
    showDivs('block', 'block', 'none', 'none', 'none');
}

function toPayment() {
    showDivs('block', 'none', 'none', 'block', 'none');
}

function finishPurchase() {
    document.getElementById("hiddenPaymentMethod").value = document.getElementById("paymentMethod").value;
    document.getElementById("hiddenDeliveryMethod").value = document.getElementById("deliveryMethod").value;

    let orderInfo = {
        addressId: document.getElementById("hiddenAddressIdForDto").value,
        paymentMethod: document.getElementById("hiddenPaymentMethod").value,
        deliveryMethod: document.getElementById("hiddenDeliveryMethod").value,
    };
    $.ajax({
        sync: true,
        method: 'POST',

        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/checkout/confirm',
        data: JSON.stringify(orderInfo)
    });
    showDivs('block', 'none', 'none', 'none', 'block');
toastr.success('Your Purchase has been registered')
}