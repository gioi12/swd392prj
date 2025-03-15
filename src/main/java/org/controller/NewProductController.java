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
import org.service.CloudinaryConfig;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet("/admin/NewProduct")
public class NewProductController extends BaseRequiredAuthenticationController{
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        req.getRequestDispatcher("/view/NewProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String filePath = getServletContext().getRealPath("/") + fileName;
        String productName = req.getParameter("productName");
        String productDes = req.getParameter("productDes");
        String productPrice = req.getParameter("productPrice");
        String productQuantity = req.getParameter("productQuantity");
        try{
            filePart.write(filePath);
            File file = new File(filePath);

            // Upload lên Cloudinary
            Cloudinary cloudinary = CloudinaryConfig.getInstance();
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            // Lấy URL ảnh sau khi upload
            String imageUrl = (String) uploadResult.get("secure_url");
            // Xóa file tạm
            file.delete();
            if(imageUrl != null){
                ProductDBContext pdb = new ProductDBContext();
                Boolean result = pdb.addProduct(productName, productPrice, productDes, imageUrl,productQuantity);
                if (result) {
                    resp.sendRedirect(req.getContextPath() + "/admin/ManageProduct");
                }
            }
        }
        catch (Exception e){
            resp.getWriter().write("Add product failed");

        }


    }
}
