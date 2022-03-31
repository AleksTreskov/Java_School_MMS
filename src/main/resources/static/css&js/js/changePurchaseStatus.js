function onChangePurchaseStatus(purchaseId) {
    let purchaseStatusSelector = document.getElementById("purchaseStatus" + purchaseId);
    let newStatus = purchaseStatusSelector.options[purchaseStatusSelector.selectedIndex].text;

    let purchaseStatusInfo = {
        purchaseId: purchaseId,
        purchaseStatus: newStatus,
    };

    $.ajax({
        sync: true,
        method: 'POST',

        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/purchases/all/changePurchaseStatus',
        data: JSON.stringify(purchaseStatusInfo)
    });
    toastr.success("Changed!")


}
