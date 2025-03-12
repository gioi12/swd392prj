package controller.authen;

import service.Constants;
import dao.GoogleLoginDAO;
import dao.UserDAO;
import dao.AccountLoginDAO;
import entity.AccountLogin;
import entity.GoogleLogin;
import entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class LoginWithGoogleHandler extends BaseRequireAuthentication {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(request.getParameter("code"));

        String code = request.getParameter("code");
        String accessToken = getToken(code);
        GoogleLogin ggaccount = getUserInfo(accessToken);

        HttpSession session = request.getSession();
        GoogleLoginDAO gldb = new GoogleLoginDAO();
        UserDAO udb = new UserDAO();
        AccountLoginDAO accountDAO = new AccountLoginDAO();

        // Kiểm tra nếu email đã tồn tại trong hệ thống
        if (accountDAO.checkEmailExist(ggaccount.getEmail())) {
            // Nếu email đã tồn tại trong hệ thống
            User existingUser = udb.getUserByEmail(ggaccount.getEmail());

            // Kiểm tra trạng thái của tài khoản (IsDelete)
            if (existingUser != null) {
                if (existingUser.getIsDelete()) {
                    // Nếu tài khoản đã bị xóa, hiển thị thông báo lỗi
                    String wrongAcc = "Tài khoản của bạn đã bị xóa!";
                    request.setAttribute("wrongAcc", wrongAcc);
                    request.setAttribute("wrongCaptcha", "");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else {
                    // Nếu tài khoản hợp lệ, đăng nhập người dùng vào hệ thống
                    session.setAttribute("user", existingUser);
                    response.sendRedirect("/LoginSystem/index.html"); // Chuyển hướng đến trang chính
                }
            }
        } else {
            // Nếu email chưa tồn tại trong hệ thống, thực hiện đăng ký
            session.setAttribute("email", ggaccount.getEmail());  // Lưu email vào session
            response.sendRedirect("/LoginSystem/register.jsp");  // Chuyển đến trang đăng ký
        }

    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        // call api to get token
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GoogleLogin getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        GoogleLogin googlePojo = new Gson().fromJson(response, GoogleLogin.class);

        return googlePojo;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, AccountLogin account) throws ServletException, IOException {
        processRequest(req, resp);
    }
 public static void main(String[] args) {
        // Giả lập thông tin email Google
        String emailFromGoogle = "longh1248@gmail.com"; // Ví dụ email lấy từ Google

        // Kiểm tra xem email đã tồn tại trong hệ thống chưa
        UserDAO udb = new UserDAO();
        AccountLoginDAO accountDAO = new AccountLoginDAO();

        if (accountDAO.checkEmailExist(emailFromGoogle)) {
            // Email đã tồn tại trong hệ thống
            User existingUser = udb.getUserByEmail(emailFromGoogle);
            if (existingUser != null && !existingUser.getIsDelete()) {
                // Tài khoản hợp lệ, hiển thị thông tin
                System.out.println("Email: " + existingUser.getEmail());
                System.out.println("User is valid and active.");
            } else {
                System.out.println("Account is deleted or does not exist.");
            }
        } else {
            // Email chưa tồn tại, hiển thị thông báo đăng ký
            System.out.println("Email is new, proceed to registration.");
        }
    }
}
