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
        <form id="order" method="post" action="confirmOrder">


            <div class="mb-4">
                <label class="block text-gray-700">Province</label>
            <select id="city" name="province" class="w-full p-2 border border-gray-300 rounded mt-1" onchange="updateProvince(this.value)">
                <option value="">Select a Province</option>
                <c:forEach var="province" items="${provinces}">

                    <option value="${province.code}" ${province.code == param.province ? 'selected' : ''}>
                            ${province.name}
                    </option>
                </c:forEach>
            </select>
            </div>


            <div class="mb-4">
                <label class="block text-gray-700">District</label>
                <select id="district" name="district" class="w-full p-2 border border-gray-300 rounded mt-1">
                    <option value="">Select a District</option>
                    <c:forEach var="dis" items="${districts}">
                        <c:if test="${dis.province_code == param.province && param.province != null}">
                        <option value="${dis.code}" ${dis.code == param.district ? 'selected' : ''}>${dis.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Ward</label>
                <select id="ward" name="ward" class="w-full p-2 border border-gray-300 rounded mt-1">
                    <option value="">Select a Ward</option>
                    <c:forEach var="ward" items="${wards}">
                        <c:if test="${ward.district_code == param.district && param.district != null}">
                            <option value="${ward.code}">${ward.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Name</label>
                <input type="text" name="name" class="w-full p-2 border border-gray-300 rounded mt-1"/>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Phone</label>
                <input type="text" name="phone" class="w-full p-2 border border-gray-300 rounded mt-1"/>
            </div>
            <div class="mb-4">
                <label class="block text-gray-700">Email</label>
                <input type="email" name="email" class="w-full p-2 border border-gray-300 rounded mt-1"/>
            </div>
            <div class="mb-4 p-2 bg-gray-100 rounded-lg">
                <p class="font-semibold">Subtotal: <span id="subtotal">$${totalPrice}</span></p>
                <p class="font-semibold">Shipping Fee: <span id="shipping-fee">$${param.fee}</span></p>
                <p class="text-lg font-bold">Total: <span id="final-total">$${param.fee+totalPrice}</span></p>
            </div>

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
        <button id="submit-button" class="bg-indigo-600 text-white px-4 py-2 rounded-lg shadow-md hover:bg-indigo-700">
            Submit
        </button>
    </div>
</div>
<script>
    document.getElementById("city").addEventListener("change", function () {
        let selectedProvince = this.value;
        let url = new URL(window.location.href);
        url.searchParams.set("province", selectedProvince);
        url.searchParams.delete("district");
        window.location.href = url.toString();
    });

    document.getElementById("district").addEventListener("change", function () {
        let selectedDistrict = this.value;
        let url = new URL(window.location.href);
        let currentProvince = url.searchParams.get("province");

        if (!currentProvince) {
            alert("Vui lòng chọn tỉnh trước!");
            return;
        }
        let shippingFee = currentProvince * 1000 + selectedDistrict * 500;
        url.searchParams.set("district", selectedDistrict);
        url.searchParams.set("fee", (shippingFee / 23000).toFixed(2));

        window.location.href = url.toString();
    });
    document.getElementById("submit-button").addEventListener("click", function (event) {
        let nameInput = document.querySelector('input[type="text"]');
        let phoneInput = document.querySelectorAll('input[type="text"]')[1];
        let emailInput = document.querySelector('input[type="email"]');
        let provinceSelect = document.getElementById("city");
        let districtSelect = document.getElementById("district");
        let wardSelect = document.getElementById("ward");

        if (!nameInput || !phoneInput || !emailInput || !provinceSelect || !districtSelect || !wardSelect) {
            alert("Error: Some form elements are missing.");
            return;
        }

        let name = nameInput.value.trim();
        let phone = phoneInput.value.trim();
        let email = emailInput.value.trim();
        let province = provinceSelect.value;
        let district = districtSelect.value;
        let ward = wardSelect.value;

        let errorMessages = [];

        if (!name) errorMessages.push("Name cannot be empty.");
        if (!phone) errorMessages.push("Phone cannot be empty.");
        if (!email) errorMessages.push("Email cannot be empty.");
        if (!province) errorMessages.push("Please select a province.");
        if (!district) errorMessages.push("Please select a district.");
        if (!ward) errorMessages.push("Please select a ward.");

        if (errorMessages.length > 0) {
            alert(errorMessages.join("\n"));
        } else {
            document.getElementById("order").submit(); // Submit form nếu hợp lệ
        }
    });
</script>
</body>
</html>
