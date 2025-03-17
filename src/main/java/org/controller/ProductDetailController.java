package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Product;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/product-detail")
public class ProductDetailController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdStr = req.getParameter("id");
        if (productIdStr == null || productIdStr.isEmpty()) {
            resp.sendRedirect("/home");
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        ProductDBContext productDB = new ProductDBContext();

        Product product = productDB.getProductById(productId);

        if (product == null) {
            resp.sendRedirect("/home");
            return;
        }

        List<Product> allProducts = productDB.getPublicProduct();

        // Xóa sản phẩm đang xem khỏi danh sách
        allProducts.removeIf(p -> p.getId() == productId);

        // Xáo trộn danh sách sản phẩm
        Collections.shuffle(allProducts);

        // Lấy 4 sản phẩm đầu tiên (hoặc ít hơn nếu danh sách nhỏ)
        List<Product> relatedProducts = allProducts.subList(0, Math.min(4, allProducts.size()));

        req.setAttribute("product", product);
        req.setAttribute("relatedProducts", relatedProducts);

        req.getRequestDispatcher("/view/ProductDetail.jsp").forward(req, resp);
    }
}
