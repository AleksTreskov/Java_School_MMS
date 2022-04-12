let recovDiv = document.getElementById("recoverPasswordDiv");
let loginDiv = document.getElementById("LoginDiv");
toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-full-width",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "1000",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

function showDiv() {

    recovDiv.style.display = "block"
    loginDiv.style.display = "none"
}

function sendCodeOnPhoneOrEmail() {
    let phoneNumber = document.getElementById("phoneNumberInput").value;
    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/auth/forgotPassword',
        data: JSON.stringify(phoneNumber)

    }).done(function (result) {
        if (!result.error) {
            toastr.info("We sent message on your phone/email, please, check it, and enter in the field below.")
            document.getElementById("phoneNumberDiv").style.display = "none";
            document.getElementById("recoveryCodeInput").style.display = "block";
        }
        if (result.error)
            toastr.error(result.message);

    });
}

function setNewPassword() {
    let newPassword = document.getElementById("newPasswordInput").value;
    let phoneNumber = document.getElementById("phoneNumberInput").value;
    let edit = {
        loginParameter:phoneNumber,
        password:newPassword
    }
    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/auth/setNewPassword',
        data: JSON.stringify(edit)

    }).done(function (result) {
        if (!result.error) {
            toastr.success("Your password has been successfully changed.")
            document.getElementById("LoginDiv").style.display = "block";
            document.getElementById("newPasswordDiv").style.display = "none";


        }
        if (result.error)
            toastr.error(result.message);

    });

}
function verifyCode() {
    const code = document.getElementById("ActivationCode").value;

    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/auth/verifyCode',
        data: JSON.stringify(code)
    }).done(function (result) {
        if (result.error) {
            toastr.error("Wrong code")
        } else {
            toastr.success("Verified!")
            document.getElementById("recoveryCodeInput").style.display="none"
            document.getElementById("newPasswordDiv").style.display="block"

        }
    });
}