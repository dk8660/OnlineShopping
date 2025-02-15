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
               quantity: parseInt(checkbox.dataset.quantity, 10),
               cartId: checkbox.dataset.id
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

    fetch("/order/newOrder", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(orderData)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/order';
            } else {
                alert("주문 처리에 실패했습니다.");
            }
        });
}