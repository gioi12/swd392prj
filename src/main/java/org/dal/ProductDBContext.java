package org.dal;

import org.entity.Product;
import org.entity.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDBContext extends DBContext{
    public boolean addProduct(String name, String price, String description, String image,String quantity) {
        String sql="INSERT INTO Product (name, description,price,time,quantity,status_id, image_url) VALUES (?, ?, ?, ?,?,?,?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, description);
            stm.setString(3, price);
            stm.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            stm.setString(5, quantity);
            stm.setString(6, "1");
            stm.setString(7, image);
            int i = stm.executeUpdate();
            if(i > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public ArrayList<Product> getAllProduct() {
        String sql = "Select p.id,p.name,description,price,time,quantity,s.id AS status_id,s.name AS status_name, image_url From Product p JOIN Status s ON p.status_id = s.id ";
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setTime(rs.getTimestamp("time"));
                product.setQuantity(rs.getInt("quantity"));
                Status s = new Status();
                s.setId(rs.getInt("status_id"));
                s.setName(rs.getString("status_name"));
                product.setStatus(s);
                product.setImage_url(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }
    public boolean updateStatusProduct(int statsId, int id) {
        String sql = "UPDATE Product SET status_id = ? WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, statsId);
            stm.setInt(2, id);

            int i = stm.executeUpdate();
            if(i > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            int i = stm.executeUpdate();
            if(i > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public Product getProductById(int id) {
        String sql = "Select p.id,p.name,description,price,time,quantity,s.id AS status_id,s.name AS status_name, image_url From Product p JOIN Status s ON p.status_id = s.id WHERE p.id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setTime(rs.getTimestamp("time"));
                product.setQuantity(rs.getInt("quantity"));
                Status s = new Status();
                s.setId(rs.getInt("status_id"));
                s.setName(rs.getString("status_name"));
                product.setStatus(s);
                product.setImage_url(rs.getString("image_url"));
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
