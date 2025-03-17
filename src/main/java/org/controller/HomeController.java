package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Product;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final int PAGE_SIZE = 8; // Số sản phẩm mỗi trang

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDBContext pb = new ProductDBContext();

        // Lấy toàn bộ danh sách sản phẩm
        List<Product> allProducts = pb.getPublicProduct();
        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        // Xác định trang hiện tại
        String pageStr = req.getParameter("page");
        int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;

        // Xác định phạm vi sản phẩm cần hiển thị
        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalProducts);
        List<Product> productsOnPage = allProducts.subList(startIndex, endIndex);

        // Gửi dữ liệu xuống JSP
        req.setAttribute("products", productsOnPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/view/Home.jsp").forward(req, resp);
    }
}
