package org.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.entity.Account;

import java.io.IOException;

public  abstract  class BaseRequiredAuthenticationController extends HttpServlet {
    private boolean isAuthenticated(HttpServletRequest req)
    {
        Account user = (Account)req.getSession().getAttribute("account");
        return user != null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAuthenticated(req))
        {
            //do business logic here
            Account user = (Account)req.getSession().getAttribute("account");
            doPost(req, resp, user);
        }
        else
            resp.sendError(403,"You do n't have right to access");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAuthenticated(req))
        {
            Account user = (Account)req.getSession().getAttribute("account");
            doGet(req, resp, user);
        }
        else
            resp.sendError(403,"You do n't have right to access");
    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException;
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException;

}
