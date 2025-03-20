<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Order Placing and Confirmation</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet"/>
</head>
<body class="font-roboto bg-gray-100">
<jsp:include page="Navbar.jsp"/>

<div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Order Confirmation</h1>

    <!-- Selected Products List -->
    <div class="bg-white p-4 rounded-lg shadow-md mb-6">
        <h2 class="text-xl font-semibold mb-4">Selected Products</h2>
        <c:forEach var="cart" items="${carts}">
            <div class="flex items-center mb-4">
                <img src="${cart.product.image_url}" alt="Image of ${cart.product.name}" class="w-16 h-16 mr-4"/>
                <div class="flex-1">
                    <h3 class="font-semibold">${cart.product.name}</h3>
                    <p class="text-gray-600">Quantity: ${cart.quantity}</p>
                </div>
                <p class="font-semibold">$${cart.product.price}</p>
            </div>
        </c:forEach>
        <div class="text-right font-bold text-lg">
            Total: $${totalPrice}
        </div>
    </div>

    <!-- Receiver Information Form -->
    <div class="bg-white p-4 rounded-lg shadow-md mb-6">
        <h2 class="text-xl font-semibold mb-4">Receiver Information</h2>
        <form>
            <div class="mb-4">
                <label class="block text-gray-700">Name</label>
                <input type="text" class="w-full p-2 border border-gray-300 rounded mt-1"/>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Phone</label>
                <input type="text" class="w-full p-2 border border-gray-300 rounded mt-1"/>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Email</label>
                <input type="email" class="w-full p-2 border border-gray-300 rounded mt-1"/>
            </div>


<%--            <div class="mb-4">--%>
<%--                <label class="block text-gray-700">City/Province</label>--%>
<%--                <select id="city" class="w-full p-2 border border-gray-300 rounded mt-1">--%>
<%--                    <option value="">Select a Province</option>--%>
<%--                    <c:forEach var="province" items="${provinces}">--%>
<%--                        <option value="${province.code}">${province.name}</option>--%>
<%--                    </c:forEach>--%>
<%--                </select>--%>
<%--            </div>--%>


<%--            <div class="mb-4">--%>
<%--                <label class="block text-gray-700">District</label>--%>
<%--                <select id="district" class="w-full p-2 border border-gray-300 rounded mt-1">--%>
<%--                    <option value="">Select a District</option>--%>
<%--                    <c:forEach var="province" items="${districts}">--%>
<%--                        <option value="${province.code}">${province.name}</option>--%>
<%--                    </c:forEach>--%>
<%--                </select>--%>
<%--            </div>--%>
<%--            <div class="mb-4">--%>
<%--                <label class="block text-gray-700">Ward</label>--%>
<%--                <select id="ward" class="w-full p-2 border border-gray-300 rounded mt-1">--%>
<%--                    <option value="">Select a Ward</option>--%>
<%--                    <c:forEach var="province" items="${wards}">--%>
<%--                        <option value="${province.code}">${province.name}</option>--%>
<%--                    </c:forEach>--%>
<%--                </select>--%>
<%--            </div>--%>

        </form>
    </div>


    <!-- Payment Method Selection -->
    <div class="bg-white p-4 rounded-lg shadow-md mb-6">
        <h2 class="text-xl font-semibold mb-4">Payment Method</h2>
        <form>
            <label class="inline-flex items-center mb-2">
                <input type="radio" name="payment" value="online" class="form-radio text-indigo-600"/>
                <span class="ml-2">Online Payment</span>
            </label>
            <br/>
            <label class="inline-flex items-center mb-2">
                <input type="radio" name="payment" value="bank" class="form-radio text-indigo-600"/>
                <span class="ml-2">Bank Transfer</span>
            </label>
            <br/>
            <label class="inline-flex items-center mb-2">
                <input type="radio" name="payment" value="cod" class="form-radio text-indigo-600"/>
                <span class="ml-2">Cash on Delivery (COD)</span>
            </label>
        </form>
    </div>

    <!-- Checkout Button -->
    <div class="text-right">
        <button class="bg-indigo-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-indigo-700">
            Checkout
        </button>
    </div>
</div>
</body>
</html>
