package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Account;
import org.entity.Product;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/ManageProduct")
public class ManageProductController extends BaseRequiredAuthenticationController {
    private static final int PRODUCTS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ProductDBContext pb = new ProductDBContext();
        List<Product> allProducts = pb.getAllProduct();

        int page = 1;
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);

        int start = (page - 1) * PRODUCTS_PER_PAGE;
        int end = Math.min(start + PRODUCTS_PER_PAGE, totalProducts);
        List<Product> productsOnPage = allProducts.subList(start, end);

        req.setAttribute("products", productsOnPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/view/ManageProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
    }
}
