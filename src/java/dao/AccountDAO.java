package dao;

import entity.AccountLogin;
import java.sql.*;

public class AccountDAO extends DBContext {

    public AccountLogin getByUsernamePassword(String username, String password) {
        try {
            String sql = "SELECT ID, UserID, UserKey, PassWord, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedByID "
                    + "FROM [dbo].[authentication] "
                    + "WHERE UserKey = ? AND PassWord = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);  // Bổ sung kiểm tra mật khẩu
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                UserDAO u = new UserDAO();
                AccountLogin account = new AccountLogin();
                account.setID(rs.getInt("ID"));
                account.setUser(u.getUserById(rs.getInt("UserID")));
                account.setUserName(username);
                account.setPassword(rs.getString("PassWord"));
                account.setCreatedAt(rs.getDate("CreatedAt"));
                account.setUpdatedAt(rs.getDate("UpdatedAt"));
                account.setUpdatedAt(rs.getDate("DeletedAt"));  // Sửa lỗi cập nhật sai
                account.setIsDelete(rs.getBoolean("IsDelete"));
                account.setDeletedByID(rs.getInt("DeletedByID"));
                return account;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Hiển thị lỗi SQL
        }
        return null;
    }
    
    
}
