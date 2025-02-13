function addToCart(productId) {
    let quantityInput = document.getElementById(`quantity-${productId}`);
    let quantity = parseInt(quantityInput.value, 10);

    if(isNaN(quantity) || quantity <= 0) {
        return
    }

    let orderInfo = {productId, quantity};
    console.log(orderInfo);
    fetch("/cart/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(orderInfo),
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
        })
        .catch(error => console.error("Error: ", error));
}

function updateTotal() {
    let total = 0;
    let checkboxes = document.querySelectorAll('.product-checkbox');
    checkboxes.forEach((checkbox) => {
       if(checkbox.checked) {
           let price = parseInt(checkbox.dataset.price);
           let quantity = parseInt(checkbox.dataset.quantity);
           total += price * quantity;
       }
    });
    document.getElementById('total-price').innerText = total + '원';
}

function submitOrder() {
    let selectedProducts = [];
    let checkboxes = document.querySelectorAll('.product-checkbox');
    checkboxes.forEach((checkbox) => {
       if(checkbox.checked) {
           selectedProducts.push({
               productId: checkbox.value,
               quantity: parseInt(checkbox.dataset.quantity, 10)
           });
       }
    });

    if(selectedProducts.length === 0) {
        alert("주문할 상품을 선택해주세요.");
        return;
    }

    let orderData = {
        totalPrice: document.getElementById('total-price').innerText.replace("원", ""),
        items: selectedProducts
    }

    let form = document.createElement("form");
    form.method = "POST";
    form.action = "/order/newOrder";
    let input = document.createElement("input");
    input.type = "hidden";
    input.name = "orderData";
    input.value = JSON.stringify(orderData);
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}