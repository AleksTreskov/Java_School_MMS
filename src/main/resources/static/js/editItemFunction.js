function editItem(id) {
    document.getElementById("editItemButton").style.display = 'none';
    document.getElementById("editItemForm").style.display = 'block';
    document.getElementById("addToCart").style.display = 'none';
    document.getElementById("saveEditItemButton").setAttribute("onClick", "javascript: saveEditItem(" + id + ");");
    const itemNameField = document.getElementById("name");
    const itemCategoryField = document.getElementById("category");
    const itemPriceField = document.getElementById("price");
    const itemQuantityField = document.getElementById("quantity");
    const descriptionNameField = document.getElementById("description");
    const brandCategoryField = document.getElementById("brand");
    const modelPriceField = document.getElementById("model");
    const weightQuantityField = document.getElementById("weight");
    // const itemSoldField = document.getElementById("sold");
    let edit = id;

    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/editItem',
        data: JSON.stringify(edit)
    }).done(function (result) {
        itemNameField.value = result.name;
        itemCategoryField.value = result.category;
        itemPriceField.value = result.amount;
        itemQuantityField.value = result.quantity;
        itemPriceField.value = result.price;
        descriptionNameField.value = result.description;
        brandCategoryField.value = result.brand;
        modelPriceField.value = result.model;
        weightQuantityField.value = result.weight;
        // itemSoldField.value = result.sold;


    });

}

function saveEditItem(id) {
    let itemNameField = document.getElementById("name");
    let itemCategoryField = document.getElementById("category");
    let itemPriceField = document.getElementById("price");
    let itemQuantityField = document.getElementById("quantity");
    let itemDescriptionField = document.getElementById("description");
    let itemBrandField = document.getElementById("brand");
    let itemModelField = document.getElementById("model");
    let itemWeightField = document.getElementById("weight");
    // let itemSoldField = document.getElementById("sold");
    let edit = {
        id: id,
        name: itemNameField.value,
        category: itemCategoryField.value,
        price: itemPriceField.value,
        quantity: itemQuantityField.value,
        description: itemDescriptionField.value,
        brand: itemBrandField.value,
        model: itemModelField.value,
        weight: itemWeightField.value,
        // sold: itemSoldField.value,
    };
    $.ajax({
        sync: true,
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: '/saveEditItem',
        data: JSON.stringify(edit)
    }).done(function (result) {
        itemNameField = document.getElementById("name" + id);
        itemCategoryField = document.getElementById("category" + id);
        itemPriceField = document.getElementById("price" + id);
        itemQuantityField = document.getElementById("amount" + id);
        itemDescriptionField = document.getElementById("description" + id);
        itemBrandField = document.getElementById("brand" + id);
        itemModelField = document.getElementById("model" + id);
        itemWeightField = document.getElementById("weight" + id);
        // itemSoldField = document.getElementById("sold" + id);
        document.getElementById("editItemForm").style.display = 'none';
        document.getElementById("addToCart").style.display = 'block';
        itemNameField.innerHTML = result.name;
        itemCategoryField.innerHTML = result.category;
        itemPriceField.innerHTML = result.price;
        itemQuantityField.innerHTML = result.quantity;
        itemDescriptionField.innerHTML = result.description;
        itemBrandField.innerHTML = result.brand;
        itemModelField.innerHTML = result.model;
        itemWeightField.innerHTML = result.weight;
        // itemSoldField.innerHTML = result.sold;

        window.location.reload();


        toastr.success("Changed!")
    });

}