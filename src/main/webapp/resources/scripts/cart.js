function addToCart(productId, productName, cost) {
    let products = sessionStorage.products;
    if(products === "null" || products === undefined) {
        products = [];
    } else {
        products = JSON.parse(products);
    }
    products = products.concat([{id: productId, name: productName, cost: cost}]);
    sessionStorage.products = JSON.stringify(products);
}

function getProductList() {
    return JSON.parse(sessionStorage.products);
}

function onSuccessfulOrdering() {
    sessionStorage.products = null;
}