<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Products</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .table img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 5px;
        }
        .container-fluid {
            padding: 30px;
        }
        .card {
            border-radius: 10px;
        }
        .action-icons i {
            cursor: pointer;
            font-size: 1.2rem;
            margin: 0 5px;
            transition: color 0.2s;
        }
        .action-icons i:hover {
            color: #007bff;
        }
    </style>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="container-fluid">
    <div class="row">
        <!-- Product List -->
        <div class="col-lg-9">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Product List</h5>
                </div>
                <div class="card-body">
                    <table class="table table-striped table-hover">
                        <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price ($)</th>
                            <th>Quantity</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="p" items="${requestScope.products}">
                            <tr>
                                <td>${p.id}</td>
                                <td><img src="${p.image_url}" alt="Product Image"></td>
                                <td>${p.name}</td>
                                <td>${p.description}</td>
                                <td class="text-success fw-bold">$${p.price}</td>
                                <td>${p.quantity}</td>
                                <td>
                                    <span class="badge bg-${p.status.id == 2 ? 'success' : 'danger'}">
                                            ${p.status.name}
                                    </span>
                                </td>
                                <td class="action-icons">
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=1&id=${p.id}" title="Publish"><i class="bi bi-upload text-success"></i></a>
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=2&id=${p.id}" title="Unpublish"><i class="bi bi-download text-warning"></i></a>
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=3&id=${p.id}" title="Remove" onclick="return confirm('Are you sure you want to delete this product?');">
                                        <i class="bi bi-trash text-danger"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=4&id=${p.id}" title="Unpublish"><i class="bi bi-download text-warning"></i></a>



                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- New Product Form -->
        <div class="col-lg-3">
            <jsp:include page="NewProduct.jsp"/>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
