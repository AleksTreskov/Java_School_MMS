function useDiscount() {
    let code = document.getElementById("discountCode").value;
    $.ajax({
        sync: true,
        method: 'POST',

        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/promo/useDiscountCode',
        data: JSON.stringify(code)
    }).done(function (result) {
        if (result.error)
            toastr.error(result.message);
        else {
            let oldSum = document.getElementById("priceInCartWithoutDiscount");

            let newSum = (1 - result.discountPercent / 100) * parseInt(oldSum.innerText);
            let discount = document.getElementById("priceInCartWithDiscount");
            document.getElementById("strongPrice").style.display = "none";
            document.getElementById("oldPriceInCart").style.display = "block";
            discount.classList.add("red")
            discount.innerText = "$" + newSum;
            discount.style.display = "block";
            toastr.success("Discount code successfully activated!")
        }
    });

}

function addPromoForm() {
    document.getElementById("newPromoForm").style.display = "block";
    document.getElementById("addNewPromoFormButton").style.display = "none";
    document.getElementById("confirmDiscountButton").style.display = "block";
}

function confirmPromo() {
    const discountNameField = document.getElementById("discountName");
    const discountDescriptionField = document.getElementById("discountDescription");
    const requiredSumField = document.getElementById("requiredSum");
    const percentDiscountField = document.getElementById("percentOfDiscount");
    let promo = {
        name: discountNameField.value,
        requiredSumForActivation: requiredSumField.value,
        percentDiscount: percentDiscountField.value,
        description: discountDescriptionField.value
    };
    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/promo/saveNewDiscount',
        data: JSON.stringify(promo)
    }).done(function (result) {
        if (result.error) {
            toastr.error(result.message)
            return;
        }
        // document.getElementById("newPromoForm").style.display="none"
        // document.getElementById("addNewPromoFormButton").style.display="block"
        // document.getElementById("confirmDiscountButton").style.display = "none";
        window.location.reload();




    })
}
function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}
function getCheckOutPage() {
    let code = document.getElementById("discountCode").value;
    $.ajax({
        sync: false,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/checkout',
        data: JSON.stringify(code)
    })
}