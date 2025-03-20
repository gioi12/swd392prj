package org.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dal.CartDBContext;
import org.dal.OrderDBContext;
import org.dal.ProductDBContext;
import org.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/confirmOrder")
public class ConfirmOrderController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        CartDBContext pbd = new CartDBContext();
        try{
            ArrayList<Cart> productByCart = pbd.getProductByCart(account.getId());
            req.setAttribute("totalPrice", productByCart.stream().filter(x -> x.getQuantity() > 0).mapToDouble(x -> x.getProduct().getPrice() * x.getQuantity()).sum());
            req.setAttribute("carts", productByCart);
            ObjectMapper objectMapper = new ObjectMapper();

            // Lấy danh sách tỉnh/thành phố
            String provincesJson = fetchAPI("https://provinces.open-api.vn/api/p");
            List<Province> provinces = objectMapper.readValue(provincesJson, new TypeReference<List<Province>>() {});
            req.setAttribute("provinces", provinces);

            // Lấy danh sách quận/huyện
            String districtsJson = fetchAPI("https://provinces.open-api.vn/api/d/");
            List<District> districts = objectMapper.readValue(districtsJson, new TypeReference<List<District>>() {});
            req.setAttribute("districts", districts);

            // Lấy danh sách phường/xã
            String wardsJson = fetchAPI("https://provinces.open-api.vn/api/w/");
            List<Ward> wards = objectMapper.readValue(wardsJson, new TypeReference<List<Ward>>() {});
            req.setAttribute("wards", wards);

        }
        catch (Exception e){
            resp.getWriter().println(e);
        }
        req.getRequestDispatcher("/view/ConfirmOrder.jsp").forward(req, resp);

    }
    private String fetchAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return response.toString();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String province = req.getParameter("province");
        String district = req.getParameter("district");
        String ward = req.getParameter("ward");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String payment = req.getParameter("payment");
        try{
            boolean pay = false;
            if(payment.equals("online"))
            {pay = true;}
            OrderDBContext odb = new OrderDBContext();
            int orderId = odb.addOrder(account.getId(), name, phone, email, district, province, ward,pay);
            CartDBContext cdb = new CartDBContext();
            cdb.updateStatusCartAndOrder(account.getId(), orderId);
            resp.getWriter().println("Oder checkout");
        }
        catch (Exception e){
            resp.getWriter().println(e);
        }
    }
}
