package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.AccountDBContext;
import org.dal.ProductDBContext;
import org.entity.Account;

import java.io.IOException;

@WebServlet("/login")
public class AccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String user =  request.getParameter("user");
            String pass =  request.getParameter("pass");
            AccountDBContext accDB = new AccountDBContext();
            Account acc = accDB.Login(user, pass);
            if(acc.getUsername() != null)
            {
                ProductDBContext pdb = new ProductDBContext();
                request.getSession().setAttribute("account", acc);
                request.getSession().setAttribute("cart", pdb.getCart(acc.getId()));

                response.sendRedirect(request.getContextPath()+"/admin/ManageProduct");
            }
            else
            {
                response.getWriter().println("login failed!");
            }

            String url = this.getInitParameter("url");
            response.getWriter().println(url);
        }
        catch (Exception e){
            response.getWriter().println(e);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



}
