<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#productModal">
    Add New Product
</button>

<!-- Modal -->
<div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="productModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;">
        <div class="modal-content" style="max-height: 90vh; overflow-y: auto;">
            <div class="modal-header">
                <h5 class="modal-title" id="productModalLabel">Add New Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="NewProduct" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label class="form-label">Image:</label>
                        <input type="file" class="form-control" name="file" id="fileInput" required onchange="previewImage()">
                        <img id="imagePreview" class="mt-2 img-fluid border rounded w-100" src="#" alt="Image Preview" style="display:none; max-width: 250px;;height: 200px">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Product Name:</label>
                        <input type="text" class="form-control" name="productName" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Product Description:</label>
                        <textarea class="form-control" name="productDes" rows="3" required></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Price ($):</label>
                        <div class="input-group">
                            <span class="input-group-text">$</span>
                            <input type="number" class="form-control" name="productPrice" min="0" step="0.01" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Quantity:</label>
                        <input type="number" class="form-control" name="productQuantity" required>
                    </div>
                    <button type="submit" class="btn btn-success w-100">Upload</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function previewImage() {
        const fileInput = document.getElementById('fileInput');
        const imagePreview = document.getElementById('imagePreview');
        const file = fileInput.files[0];

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                imagePreview.src = e.target.result;
                imagePreview.style.display = 'block';
            }
            reader.readAsDataURL(file);
        }
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
