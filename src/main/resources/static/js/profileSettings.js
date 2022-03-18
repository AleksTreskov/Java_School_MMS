

/*
Addresses functions
 */
async function editAddress(id) {
    document.getElementById("newAddressForm").style.display = 'block';
    document.getElementById("addNewAddressButton").style.display = 'none';
    document.getElementById("saveEditAddressButton").style.display = 'block';
    document.getElementById("saveEditAddressButton").setAttribute("onClick", "javascript: saveEditAddress(" + id + ");");
    const countryField = document.getElementById("country");
    const cityField = document.getElementById("city");
    const streetField = document.getElementById("street");
    const buildingField = document.getElementById("building");
    const flatField = document.getElementById("flat");
    const postcodeField = document.getElementById("postcode");


    let call = await fetch('profile/editAddress', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(id)
    });

    let result = await call.json();

    countryField.value = result.country;
    cityField.value = result.city;
    streetField.value = result.street;
    buildingField.value = result.building;
    flatField.value = result.flat;
    postcodeField.value = result.postcode;

}

function deleteAddress(id) {
    const deleteAddressRow = document.getElementById("row" + id);
    let deleteId = id;

    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'profile/deleteAddressById',
        data: JSON.stringify(deleteId)
    }).done(function (result) {
            if (!result.error) {
                deleteAddressRow.parentNode.removeChild(deleteAddressRow);
            } else {

                toastr.error(result.message)
            }

        });
}

function addAddress() {
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'block';
    document.getElementById("addNewAddressButton").style.display = 'none';
    document.getElementById("saveNewAddressButton").style.display = 'block';
}

function cancelAddress() {
    clearNewAddressForm();
    document.getElementById("newAddressForm").style.display = 'none';
    document.getElementById("addNewAddressButton").style.display = 'block';
    document.getElementById("saveEditAddressButton").style.display = 'none';
    document.getElementById("saveNewAddressButton").style.display = 'none';

}

function saveNewAddress() {

    const countryField = document.getElementById("country");
    const cityField = document.getElementById("city");
    const streetField = document.getElementById("street");
    const buildingField = document.getElementById("building");
    const flatField = document.getElementById("flat");
    const postcodeField = document.getElementById("postcode");
    const table = document.getElementById("addressTable");

    let newAddress = {
        country: countryField.value,
        city: cityField.value,
        street: streetField.value,
        building: buildingField.value,
        flat: flatField.value,
        postcode: postcodeField.value
    };

    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'profile/saveNewAddress/',
        data: JSON.stringify(newAddress)
    }).done(function (result) {
        if (result.error){
            toastr.error(result.message);
            return;
        }
        var NewRow = table.insertRow();
        NewRow.id = "row" + result.id;
        var Newcell1 = NewRow.insertCell(0);
        var Newcell2 = NewRow.insertCell(1);
        var Newcell3 = NewRow.insertCell(2);
        var Newcell4 = NewRow.insertCell(3);
        var Newcell5 = NewRow.insertCell(4);
        var Newcell6 = NewRow.insertCell(5);
        var Newcell7 = NewRow.insertCell(6);
        var Newcell8 = NewRow.insertCell(7);
        Newcell1.innerHTML = result.country;
        Newcell2.innerHTML = result.city;
        Newcell3.innerHTML = result.street;
        Newcell4.innerHTML = result.building;
        Newcell5.innerHTML = result.flat;
        Newcell6.innerHTML = result.postcode;
        Newcell7.innerHTML = "<button type=\"button\" class=\"row btn btn-primary\" onclick=\"editAddress('" + result.id + "');\">Edit</button>";
        Newcell8.innerHTML = "<button type=\"button\" class=\"row btn btn-danger\" onclick=\"deleteAddress('" + result.id + "');\">Delete</button>";
        document.getElementById("newAddressForm").style.display = 'block';
        document.getElementById("saveNewAddressButton").style.display = 'none';
    });
}

function saveEditAddress(id) {
    let countryField = document.getElementById("country");
    let cityField = document.getElementById("city");
    let streetField = document.getElementById("street");
    let buildingField = document.getElementById("building");
    let flatField = document.getElementById("flat");
    let postcodeField = document.getElementById("postcode");
    let edit = {
        id: id,
        country: countryField.value,
        city: cityField.value,
        street: streetField.value,
        building: buildingField.value,
        flat: flatField.value,
        postcode: postcodeField.value
    };

    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'profile/saveEditAddress/',
        data: JSON.stringify(edit)
    }).done(function (result) {
        if (result.error){
            toastr.info(result.message);
            return;
        }
        countryField = document.getElementById("country" + id);
        cityField = document.getElementById("city" + id);
        streetField = document.getElementById("street" + id);
        buildingField = document.getElementById("building" + id);
        flatField = document.getElementById("flat" + id);
        postcodeField = document.getElementById("postcode" + id);
        countryField.innerText = result.country;
        cityField.innerText = result.city;
        streetField.innerText = result.street;
        buildingField.innerText = result.building;
        flatField.innerText = result.flat;
        postcodeField.innerText = result.postcode;
        document.getElementById("newAddressForm").style.display = 'none';
        document.getElementById("addNewAddressButton").style.display = 'block';
        document.getElementById("saveEditAddressButton").style.display = 'none';
    });
}

function editMain() {
    switchMainSettingFields(false);
    switchMainSettingButtons('none', 'block');
}

function saveMainEdit() {

    const firstNameField = document.getElementById("firstName");
    const lastNameField = document.getElementById("lastName");
    let edit = {
        name: firstNameField.value,
        surname: lastNameField.value,
    };
    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'profile/updateMainInfo/',
        data: JSON.stringify(edit)
    });

    switchMainSettingFields(true);
    switchMainSettingButtons('block', 'none');


}

function switchMainSettingFields(readonlyBool) {
    const firstNameField = document.getElementById("firstName");
    const lastNameField = document.getElementById("lastName");

    firstNameField.readOnly = readonlyBool;
    lastNameField.readOnly = readonlyBool;

    firstNameField.classList.toggle("form-control");
    lastNameField.classList.toggle("form-control");
    firstNameField.classList.toggle("form-control-plaintext");
    lastNameField.classList.toggle("form-control-plaintext");

}

function switchMainSettingButtons(edit, save) {
    const editMainButton = document.getElementById("editMainButton");
    const saveMainButton = document.getElementById("saveMainButton");
    editMainButton.style.display = edit;
    saveMainButton.style.display = save;
}

/*
Profile password change
 */
function editPassword() {
    switchPasswordFields('block');
    switchPasswordButtons('none', 'block');
}


function savePasswordEdit() {

    const currentPassword = document.getElementById("currentPassword");
    const newPassword = document.getElementById("newPassword");
    let edit = {
        currPassword: currentPassword.value,
        newPassword: newPassword.value
    };
    $.ajax({
        sync: true,
        method: 'POST',

        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'profile/changePassword',
        data: JSON.stringify(edit)
    }).done(function (result) {
        if (result.error) {
            toastr.error(result.message)
        } else {
            switchPasswordFields('none');
            switchPasswordButtons('block', 'none');
        }
    });

}

function switchPasswordButtons(edit, save) {
    const editPasswordButton = document.getElementById("editPasswordButton");
    const savePasswordButton = document.getElementById("savePasswordButton");
    editPasswordButton.style.display = edit;
    savePasswordButton.style.display = save;
}

function switchPasswordFields(display) {
    const currentPassword = document.getElementById("currentPassword");
    const newPassword = document.getElementById("newPassword");
    const currentPasswordLabel = document.getElementById("currentPasswordLabel");
    const newPasswordLabel = document.getElementById("newPasswordLabel");

    currentPassword.style.display = display;
    newPassword.style.display = display;
    currentPasswordLabel.style.display = display;
    newPasswordLabel.style.display = display;

}

function clearNewAddressForm() {
    document.getElementById("country").value = null;
    document.getElementById("city").value = null;
    document.getElementById("street").value = null;
    document.getElementById("building").value = null;
    document.getElementById("postcode").value = null;
    document.getElementById("flat").value = null;
}
