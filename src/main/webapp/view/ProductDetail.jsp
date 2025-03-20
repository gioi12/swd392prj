<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Product Detail</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"/>
</head>
<body class="font-roboto bg-gray-100 flex flex-col min-h-screen">

<jsp:include page="Navbar.jsp"/>

<!-- Main Content -->
<main class="flex-grow my-8">
    <div class="container mx-auto px-6">
        <div class="md:flex md:items-center bg-white p-6 rounded-lg shadow-lg">
            <!-- Product Image -->
            <div class="w-full h-64 md:w-1/2 lg:h-96 flex justify-center">
                <img src="${product.image_url}" alt="${product.name}" class="h-full w-auto rounded-md object-cover"/>
            </div>
            <div class="w-full max-w-lg mx-auto mt-5 md:mt-0 md:ml-8 md:w-1/2">
                <h1 class="text-gray-900 text-2xl font-bold">${product.name}</h1>
                <p class="text-gray-600 text-sm mt-2">${product.description}</p>

                <div class="mt-4">
                    <span class="text-lg font-semibold text-indigo-600">$${product.price}</span>
                    <span class="text-gray-500 text-sm ml-2">• Available: ${product.quantity}</span>
                </div>

                <hr class="my-4"/>

                <c:if test="${product.quantity > 0}">
                    <!-- Quantity Selector -->
                    <div class="mt-2">
                        <label class="text-gray-700 text-sm font-medium">Quantity:</label>
                        <div class="flex items-center mt-1">
                            <button id="decreaseQty" class="text-gray-500 focus:outline-none hover:text-red-600 text-xl">
                                <i class="fas fa-minus-circle"></i>
                            </button>
                            <span id="quantity" class="text-gray-900 text-lg mx-3 px-3 py-1 border border-gray-300 rounded-md bg-gray-100">
                                1
                            </span>
                            <button id="increaseQty" class="text-gray-500 focus:outline-none hover:text-green-600 text-xl">
                                <i class="fas fa-plus-circle"></i>
                            </button>
                        </div>
                    </div>
                    <c:set var="exists" value="false" />
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <c:if test="${item.product.id == param.id}">
                            <c:set var="exists" value="true" />
                        </c:if>
                    </c:forEach>


                    <div class="flex items-center mt-6 space-x-4">
                        <c:if test="${exists}">
                            <p class="text-red-600 font-semibold bg-red-100 border border-red-400 p-2 rounded-md">
                                🔴 Sản phẩm đã có trong giỏ hàng!
                            </p>
                        </c:if>
                        <c:if test="${!exists}">
                            <button id="addToCartBtn" class="px-8 py-2 bg-indigo-600 text-white text-sm font-medium rounded hover:bg-indigo-500 focus:outline-none focus:bg-indigo-500">
                                Add to Cart
                            </button>
                        </c:if>

                    </div>
                </c:if>

                <c:if test="${product.quantity == 0}">
                    <div class="flex items-center mt-6">
                        <span class="px-8 py-2 bg-red-500 text-white text-sm font-medium rounded">Sold Out</span>
                    </div>
                </c:if>
            </div>
        </div>

        <div class="mt-16">
            <h3 class="text-gray-600 text-2xl font-medium">More Products</h3>
            <div class="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                <c:forEach var="p" items="${relatedProducts}">
                    <div class="w-full max-w-sm mx-auto rounded-md shadow-md overflow-hidden bg-white hover:shadow-lg transition"
                         style="cursor: pointer" onclick="location.href='/OSS392/product-detail?id=${p.id}'">
                        <div class="flex items-end justify-end h-56 w-full bg-cover"
                             style="background-image: url('${p.image_url}');">
                        </div>
                        <div class="px-5 py-3">
                            <h3 class="text-gray-700 uppercase font-semibold">${p.name}</h3>
                            <span class="text-gray-500 mt-2">$${p.price}</span>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>

<footer class="bg-white shadow-md py-4 mt-8">
    <div class="container mx-auto px-6 flex justify-between items-center">
        <span class="text-gray-600 text-sm">© 2025 OSS by Group4. All rights reserved.</span>
        <div class="flex space-x-3">
            <a href="#" class="text-gray-600 hover:text-gray-800"><i class="fab fa-facebook-f"></i></a>
            <a href="#" class="text-gray-600 hover:text-gray-800"><i class="fab fa-twitter"></i></a>
            <a href="#" class="text-gray-600 hover:text-gray-800"><i class="fab fa-instagram"></i></a>
        </div>
    </div>
</footer>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const decreaseBtn = document.getElementById("decreaseQty");
        const increaseBtn = document.getElementById("increaseQty");
        const quantitySpan = document.getElementById("quantity");
        const buyNowBtn = document.getElementById("buyNowBtn");
        const addToCartBtn = document.getElementById("addToCartBtn");

        if (decreaseBtn && increaseBtn && quantitySpan) {
            let quantity = 1;
            const maxQuantity = ${product.quantity};

            decreaseBtn.addEventListener("click", function () {
                if (quantity > 1) {
                    quantity--;
                    quantitySpan.textContent = quantity;
                }
            });

            increaseBtn.addEventListener("click", function () {
                if (quantity < maxQuantity) {
                    quantity++;
                    quantitySpan.textContent = quantity;
                }
            });
        }


        if (addToCartBtn) {
            addToCartBtn.addEventListener("click", function () {
                const quantity = document.getElementById("quantity")?.textContent.trim() || 1;
                window.location.href = `${pageContext.request.contextPath}/addCart?productId=${product.id}&quantity=`+quantity;
            });
        }
    });
</script>

</body>
</html>
