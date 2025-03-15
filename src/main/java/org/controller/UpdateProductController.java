package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Account;
import org.entity.Product;

import java.io.IOException;

@WebServlet("/admin/UpdateProduct")
public class UpdateProductController extends BaseRequiredAuthenticationController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String action = req.getParameter("action");
        String productId = req.getParameter("id");
        ProductDBContext pb = new ProductDBContext();
        try{
            if(action.equals("1")) {
                boolean p = pb.updateStatusProduct(2, Integer.parseInt(productId));
                if(p) {
                    resp.sendRedirect(req.getContextPath()+"/admin/ManageProduct");
                }
            }
            if(action.equals("2")) {
                boolean p = pb.updateStatusProduct(1, Integer.parseInt(productId));
                if(p) {
                    resp.sendRedirect(req.getContextPath()+"/admin/ManageProduct");
                }
            }
            if(action.equals("3")) {
                boolean p = pb.deleteProduct( Integer.parseInt(productId));
                if(p) {
                    resp.sendRedirect(req.getContextPath()+"/admin/ManageProduct");
                }
            }
            if(action.equals("4")) {
                String pId = req.getParameter("id");
                Product p = pb.getProductById( Integer.parseInt(pId));
                req.setAttribute("product", p);
                req.getRequestDispatcher("/view/UpdateProduct.jsp").forward(req, resp);
            }
        }
        catch(Exception e) {
            resp.getWriter().println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }
}
