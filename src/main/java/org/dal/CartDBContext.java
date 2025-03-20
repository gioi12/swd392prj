package org.dal;

import org.entity.Cart;
import org.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDBContext extends DBContext{
    public ArrayList<Cart> getProductByCart(int accountId) {
        String sql = "Select p.id AS product_id, p.name, p.price, p.image_url, c.account_id, c.quantity AS cartQuantity ,p.quantity AS productQuantity From Cart c JOIN Product p ON c.product_id = p.id Where account_id = ? and c.status_id = 1";
        ArrayList<Cart> products = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, accountId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImage_url(rs.getString("image_url"));
                product.setQuantity(rs.getInt("productQuantity"));
                Cart cart = new Cart();
                cart.setProduct(product);
                cart.setQuantity(rs.getInt("cartQuantity"));
                products.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public void updateStatusCartAndOrder(int accountId, int orderId) {
        String sql = "UPDATE Cart SET status_id = 2, order_id = ? WHERE account_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, orderId);
            stm.setInt(2, accountId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating cart status", e);
        }
    }
}
