package org.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.ProductDBContext;
import org.entity.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        Gson gson = new Gson();

        try {
            // Đọc JSON từ request body
            BufferedReader reader = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Chuyển thành JSON object
            JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();

            // Lấy dữ liệu từ JSON
            int id = jsonObject.get("id").getAsInt();
            int quantity = jsonObject.get("quantity").getAsInt();

            // Xử lý cập nhật giỏ hàng
            ProductDBContext pdb = new ProductDBContext();
            boolean cart = pdb.updateCart(id, account.getId(), quantity);
            req.getSession().setAttribute("cart", pdb.getCart(account.getId()));

            // Trả về JSON phản hồi
            String json = gson.toJson(new ResponseModel(cart, null,quantity));
            out.print(json);

        } catch (Exception e) {
            String json = gson.toJson(new ResponseModel(false, e.getMessage(), 0));
            out.print(json);
        } finally {
            out.flush();
        }
    }

    // Model giúp định dạng JSON đúng
    class ResponseModel {
        boolean success;
        String error;
        int quantity;
        public ResponseModel(boolean success, String error, int quantity) {
            this.success = success;
            this.error = error;
            this.quantity = quantity;
        }
    }

}
