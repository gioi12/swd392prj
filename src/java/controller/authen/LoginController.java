package controller.authen;

import dao.AccountDAO;
import dao.UserDAO;
import entity.AccountLogin;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginController extends BaseRequireAuthentication {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        
        AccountDAO Adb = new AccountDAO();
        AccountLogin thisAccount = Adb.getByUsernamePassword(username, password);

        // Kiểm tra tài khoản và mật khẩu
        if (thisAccount == null) {
            String wrongAcc = "Tài khoản hoặc mật khẩu sai hoặc không tồn tại!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (!password.equalsIgnoreCase(thisAccount.getPassword())) {
            String wrongAcc = "Mật khẩu sai!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (thisAccount.getIsDelete() == true) {
            String wrongAcc = "Tài khoản của bạn đã bị xóa!";
            request.setAttribute("wrongAcc", wrongAcc);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            session.setAttribute("account", thisAccount);

            // Xóa cookie nếu có
            Cookie usernameCookie = new Cookie("username", "");
            Cookie passwordCookie = new Cookie("password", "");
            Cookie c_rem = new Cookie("crem", "");
            usernameCookie.setMaxAge(0);  // Hủy cookie
            passwordCookie.setMaxAge(0);  // Hủy cookie
            c_rem.setMaxAge(0);  // Hủy cookie
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
            response.addCookie(c_rem);

            UserDAO userDao = new UserDAO();
            User user = (User) userDao.getUserById(thisAccount.getUser().getID());

            // Xử lý chuyển hướng sau khi đăng nhập
            String productDetailID = "";
            if (request.getParameter("pid") != null) {
                productDetailID = (String) request.getParameter("pid");
            }

            if (productDetailID.isBlank() || productDetailID.isEmpty()) {
                response.sendRedirect("/LoginSystem/index.html");
            } else {
                response.sendRedirect("productdetail?id=" + productDetailID);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, AccountLogin account) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
