function showDivs(cartDiv, deliveryDiv, chosenAddressDiv, paymentDiv,finalDiv) {
    let cart = document.getElementById("cartDiv");
    let delivery = document.getElementById("deliveryDiv");
     let payment = document.getElementById("paymentDiv");
      let final = document.getElementById("finalDiv");
    let chosen = document.getElementById("addressChosenDiv");

    cart.style.display = cartDiv;
    delivery.style.display = deliveryDiv;
    payment.style.display = paymentDiv;
    chosen.style.display = chosenAddressDiv;
}
function chooseAddress(addressId) {
    showDivs('block', 'none', 'block', 'none','none');
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
    showDivs('block', 'block', 'none', 'none','none');
}
 function toPayment() {
    showDivs('block', 'none', 'none', 'block','none');
}
function finishOrder() {
    document.getElementById("hiddenPaymentMethod").value = document.getElementById("paymentMethod").value;
    document.getElementById("hiddenDeliveryMethod").value = document.getElementById("deliveryMethod").value;

    let orderInfo = {
        addressId: document.getElementById("hiddenAddressIdForDto").value,
        paymentMethod: document.getElementById("hiddenPaymentMethod").value,
        deliveryMethod: document.getElementById("hiddenDeliveryMethod").value,
    };

    let call = fetch("/checkout/confirm", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(orderInfo)
    });
        showDivs('block', 'none', 'none', 'none','block');


}