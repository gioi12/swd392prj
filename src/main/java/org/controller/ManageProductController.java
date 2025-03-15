package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Account;
import org.entity.Product;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/ManageProduct")
public class ManageProductController extends BaseRequiredAuthenticationController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ProductDBContext pb = new ProductDBContext();
        ArrayList<Product> products =pb.getAllProduct();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/view/ManageProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
