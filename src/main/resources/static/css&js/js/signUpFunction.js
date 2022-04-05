function showDivs(codeButtonDisp, signUpButtonDisp, verifyCodeButtonDisp, enterFieldDisp) {
    let codeButton = document.getElementById("activationCodeButton");
    let verifyCodeButton = document.getElementById("verifyCodeButton");
    let signUpButton = document.getElementById("signUpButton");
    let enterField = document.getElementById("enterCode");

    codeButton.style.display = codeButtonDisp;
    signUpButton.style.display = signUpButtonDisp;
    enterField.style.display = enterFieldDisp;
}

function sendCodeOnEmail() {
    const email = document.getElementById("email").value;

    showDivs("none", "none", "block", "block")
    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/auth/sendActivationCode',
        data: JSON.stringify(email)

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
            showDivs("none", "block", "none", "none")
        }
    });
}