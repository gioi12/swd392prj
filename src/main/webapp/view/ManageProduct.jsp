<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        body { background-color: #f8f9fa; }
        .table img { width: 50px; height: 50px; object-fit: cover; border-radius: 5px; }
        .container-fluid { padding: 30px; }
        .card { border-radius: 10px; }
        .action-icons i { cursor: pointer; font-size: 1.2rem; margin: 0 5px; transition: color 0.2s; }
        .action-icons i:hover { color: #007bff; }
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
                    <table class="table table-striped table-hover" style="height: 70vh">
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
                                    <a href="#" data-bs-toggle="modal" data-bs-target="#editProductModal${p.id}" title="Edit">
                                        <i class="bi bi-pencil-square text-primary"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=1&id=${p.id}" title="Publish">
                                        <i class="bi bi-upload text-success"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=2&id=${p.id}" title="Unpublish">
                                        <i class="bi bi-download text-warning"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/UpdateProduct?action=3&id=${p.id}" title="Remove"
                                       onclick="return confirm('Are you sure you want to delete this product?');">
                                        <i class="bi bi-trash text-danger"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <nav>
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="ManageProduct?page=${currentPage - 1}">Previous</a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="ManageProduct?page=${i}">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="ManageProduct?page=${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>

        </div>
        <!-- New Product Form -->
        <div class="col-lg-3">
            <jsp:include page="NewProduct.jsp"/>
        </div>
    </div>


    </div>
</div>

<!-- Modals for Editing Products -->
<c:forEach var="p" items="${requestScope.products}">
    <div class="modal fade" id="editProductModal${p.id}" tabindex="-1" aria-labelledby="editProductLabel${p.id}" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content" style="max-height: 90vh; overflow-y: auto;">
                <div class="modal-header">
                    <h5 class="modal-title" id="editProductLabel${p.id}">Edit Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="UpdateProduct" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="${p.id}" />

                        <!-- Image Preview -->
                        <div class="mb-3">
                            <label class="form-label">Image:</label>
                            <input type="file" class="form-control" name="file" id="fileInput${p.id}" onchange="previewImageEdit('${p.id}')">
                            <img id="imagePreview${p.id}" class="mt-2 img-fluid border rounded w-100" src="${p.image_url}" alt="Image Preview" style="max-width: 250px; height: 200px;">
                        </div>

                        <!-- Name -->
                        <div class="mb-3">
                            <label class="form-label">Product Name:</label>
                            <input type="text" class="form-control" name="name" value="${p.name}" required>
                        </div>

                        <!-- Description -->
                        <div class="mb-3">
                            <label class="form-label">Product Description:</label>
                            <textarea class="form-control" name="description" rows="3" required>${p.description}</textarea>
                        </div>

                        <!-- Price -->
                        <div class="mb-3">
                            <label class="form-label">Price ($):</label>
                            <div class="input-group">
                                <span class="input-group-text">$</span>
                                <input type="number" class="form-control" name="price" value="${p.price}" min="0" step="0.01" required>
                            </div>
                        </div>

                        <!-- Quantity -->
                        <div class="mb-3">
                            <label class="form-label">Quantity:</label>
                            <input type="number" class="form-control" name="quantity" value="${p.quantity}" min="0" required>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Save Changes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<!-- Image Preview Script -->
<script>
    function previewImageEdit(productId) {
        const fileInput = document.getElementById('fileInput' + productId);
        const imagePreview = document.getElementById('imagePreview' + productId);
        const file = fileInput.files[0];

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    }
</script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
