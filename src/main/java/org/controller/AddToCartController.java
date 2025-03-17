package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Account;

import java.io.IOException;

@WebServlet("/addCart")
public class AddToCartController extends BaseRequiredAuthenticationController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");
        try{
            ProductDBContext pdb = new ProductDBContext();
            pdb.addCart(Integer.parseInt(productIdStr), account.getId(), Integer.parseInt(quantityStr));
            req.getSession().removeAttribute("cart");
            req.getSession().setAttribute("cart", pdb.getCart(account.getId()));
            resp.sendRedirect(req.getContextPath()+"/product-detail?id="+quantityStr);
        }
        catch (Exception e){
            resp.getWriter().println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

    }
}
