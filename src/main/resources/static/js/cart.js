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