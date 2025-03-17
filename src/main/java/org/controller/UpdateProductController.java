package org.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.dal.ProductDBContext;
import org.entity.Account;
import org.entity.Product;
import org.service.CloudinaryConfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet("/admin/UpdateProduct")
public class UpdateProductController extends BaseRequiredAuthenticationController {
    private static final long serialVersionUID = 1L;
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
            else if(action.equals("2")) {
                boolean p = pb.updateStatusProduct(1, Integer.parseInt(productId));
                if(p) {
                    resp.sendRedirect(req.getContextPath()+"/admin/ManageProduct");
                }
            }
            else if(action.equals("3")) {
                boolean p = pb.deleteProduct( Integer.parseInt(productId));
                if(p) {
                    resp.sendRedirect(req.getContextPath()+"/admin/ManageProduct");
                }
            }
        }
        catch(Exception e) {
            resp.getWriter().println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String productId = req.getParameter("id");
        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String filePath = getServletContext().getRealPath("/") + fileName;
        String productName = req.getParameter("name");
        String productDes = req.getParameter("description");
        String productPrice = req.getParameter("price");
        String productQuantity = req.getParameter("quantity");
        ProductDBContext pdb = new ProductDBContext();
        try {
            filePart.write(filePath);
            File file = new File(filePath);

            Cloudinary cloudinary = CloudinaryConfig.getInstance();
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            file.delete();
            boolean b = pdb.updateProduct(Integer.parseInt(productId), productName, productDes, productPrice, productQuantity, imageUrl);
            if (b) {
                resp.sendRedirect(req.getContextPath()+"/admin/ManageProduct");
            }
        }
        catch (Exception e) {
            resp.getWriter().println(e);
        }
    }
}
