<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Import Bootstrap & FontAwesome -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>

<style>
    .navbar {
        padding: 8px 16px;
    }
    .navbar-brand {
        font-size: 1.2rem;
        font-weight: bold;
    }
    .navbar-nav .nav-link {
        padding: 0.5rem 1rem;
    }

    /* Giỏ hàng dropdown */
    .cart-dropdown {
        position: absolute;
        top: 50px;
        right: 0;
        width: 25%;
        max-width: 350px;
        background: white;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        border-radius: 8px;
        padding: 10px;
        display: none;
        z-index: 1000;
    }
    .cart-dropdown.active {
        display: block;
    }
    .cart-item {
        display: flex;
        align-items: center;
        padding: 10px 0;
        border-bottom: 1px solid #ddd;
    }
    .cart-item img {
        width: 50px;
        height: 50px;
        object-fit: cover;
        border-radius: 5px;
        margin-right: 10px;
    }
    .cart-item .cart-details {
        flex-grow: 1;
    }
    .cart-item .cart-details h6 {
        margin: 0;
        font-size: 0.9rem;
    }
    .cart-item .cart-details span {
        font-size: 0.8rem;
        color: gray;
    }
    .cart-footer {
        text-align: center;
        padding: 10px;
    }
</style>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid" style="padding: 5px">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home">OSS</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
            </li>
            <c:if test="${sessionScope.account.username eq 'admin'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/ManageProduct">Manage Products</a>
                </li>
            </c:if>
        </ul>

            <c:choose>
                <c:when test="${sessionScope.account != null}">

                    <ul class="navbar-nav ms-auto" style="margin-right: 10px">
                        <li class="nav-item">
                            <a href="#" id="cartIcon" class="nav-link position-relative">
                                <i class="fas fa-shopping-cart fa-lg"></i>
                                <span class="badge bg-danger rounded-pill position-absolute top-0 start-100 translate-middle">
                                        ${sessionScope.cart.size()}
                                </span>
                            </a>

                            <div id="cartDropdown" class="cart-dropdown">
                                <div class="cart-header d-flex justify-content-between">
                                    <strong>Your Cart</strong>
                                    <button id="closeCart" class="btn-close"></button>
                                </div>
                                <hr>
                                <div class="cart-items">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.cart}">
                                            <c:forEach var="item" items="${sessionScope.cart}">
                                                <div class="cart-item">
                                                    <img src="${item.product.image_url}" alt="${item.product.name}">
                                                    <div class="cart-details">
                                                        <h6>${item.product.name}</h6>
                                                        <span>$${item.product.price}</span>
                                                        <span>Available :${item.product.quantity}</span>

                                                    </div>
                                                    <div class="cart-actions">
                                                        <button class="btn btn-sm btn-outline-secondary" onclick="updateQuantity(${item.product.id},${item.product.quantity},0)">-</button>
                                                        <span id="qty-${item.product.id}">${item.quantity}</span>
                                                        <button class="btn btn-sm btn-outline-secondary" onclick="updateQuantity(${item.product.id},${item.product.quantity},1)">+</button>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="text-center text-muted">Your cart is empty</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="cart-footer">
                                    <a href="${pageContext.request.contextPath}/confirmOrder" class="btn btn-primary btn-sm w-100">View Cart</a>
                                </div>
                            </div>
                        </li>
                    </ul>


                        <li class="nav-item dropdown mb-2">
                            <a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                    ${sessionScope.account.username}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profile</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/orders">Orders</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                        </li>
                    </c:otherwise>
                </c:choose>
    </div>
</nav>

<!-- Bootstrap & JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const cartIcon = document.getElementById("cartIcon");
        const cartDropdown = document.getElementById("cartDropdown");
        const closeCart = document.getElementById("closeCart");

        if (cartIcon) {
            cartIcon.addEventListener("click", function (e) {
                e.preventDefault();
                cartDropdown.classList.toggle("active");
            });
        }

        if (closeCart) {
            closeCart.addEventListener("click", function () {
                cartDropdown.classList.remove("active");
            });
        }

        // Đóng dropdown khi click bên ngoài
        document.addEventListener("click", function (e) {
            if (!cartDropdown.contains(e.target) && !cartIcon.contains(e.target)) {
                cartDropdown.classList.remove("active");
            }
        });
    });
    function updateQuantity(id ,maxQuantity,action) {
        let qtyElement = document.getElementById(`qty-`+id);                                 
        let currentQuantity = Number(qtyElement.innerText);
        let newQuantity = action === 1
            ? Math.min(maxQuantity,parseInt(currentQuantity) + 1)
            : Math.max(0, parseInt(currentQuantity)  - 1);

        fetch('http://localhost:9999/OSS392/addCart', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: id, quantity: newQuantity })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    document.getElementById('qty-' + id).innerText = data.quantity;
                } else {
                    alert("Cập nhật thất bại! Lý do: " + data.error);
                }
            })
            .catch(error => console.error("Lỗi fetch: ", error));
    }

</script>
