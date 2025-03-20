package org.dal;

import org.entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class OrderDBContext extends DBContext{
    public int addOrder(int accountId, String name, String phone, String email, String district, String province, String ward,boolean pay) {
        String sql = "INSERT INTO [Order] (buyer_id, name, phone, email, district, province, ward,time,isPayment,payment,status_id) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        LocalDateTime time = LocalDateTime.now();
        try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, accountId);
            stm.setString(2, name);
            stm.setString(3, phone);
            stm.setString(4, email);
            stm.setString(5, district);
            stm.setString(6, province);
            stm.setString(7, ward);
            stm.setTimestamp(8, java.sql.Timestamp.valueOf(time));
            stm.setBoolean(9, false);
            stm.setBoolean(10, pay);
            stm.setInt(11, 1);
            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // Lấy ID vừa được tạo
            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting order", e);
        }
    }
}
