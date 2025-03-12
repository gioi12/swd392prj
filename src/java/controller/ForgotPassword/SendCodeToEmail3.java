package controller.ForgotPassword;

import dao.EmailDAO;
import dao.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class SendCodeToEmail3 extends HttpServlet {

    public static ConcurrentHashMap<String, String> otpHash = new ConcurrentHashMap<String, String>();

    public String getOtpById(String idOtp) {
        String otp = otpHash.get(idOtp);
        return otp;
    }

    public static void removeOtpCode(String idOtp) {
        otpHash.remove(idOtp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendCodeToEmail2</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendCodeToEmail2 at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String email = request.getParameter("email");

        HttpSession session = request.getSession();
        
        // Remove captcha-related code
        // String captcha = request.getParameter("captcha");
        // String idCaptcha = (String) request.getParameter("idCaptcha");
        // String codeCaptcha = classCaptcha.getCaptchaById(idCaptcha);

        // Check if email is empty
        if (email == null || email.equals("")) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin");
            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
        }

        UserDAO dao = new UserDAO();
        User useremail = dao.getUserByEmail(email);

        // Check if email exists
        if (useremail == null) {
            request.setAttribute("error", "Email không tồn tại");
            request.getRequestDispatcher("requestchangepass.jsp").forward(request, response);
        } else {
            int userid = useremail.getID();
            String otprequest = EmailDAO.generateRandomString();
            String otpId = EmailDAO.generateRandomString();    

            otpHash.put(otpId, otprequest);

            request.setAttribute("userId", userid);
            request.setAttribute("email", email);
            request.setAttribute("idOtp", otpId);

            EmailDAO.sendEmail(email, "Mã xác nhận đặt lại mật khẩu", "Chào bạn, mã OTP xác thực tài khoản của bạn là: " + "<b>" + otprequest + "</b>" + ". Mã có giá trị trong 60s.");
            request.getRequestDispatcher("verifyemailrequest.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
