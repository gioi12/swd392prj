package dao;

import static dao.DBContext.connection;
import entity.GoogleLogin;
import entity.*;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {

    public User getUserById(int id) {
        String sql = "SELECT ID, FirstName, LastName, Email, Phone, RoleID, "
                + "GetNotifications, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy "
                + "FROM dbo.[User] WHERE ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                RoleDAO r = new RoleDAO();
                return new User(
                        rs.getInt("ID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        r.getUserRoleById(rs.getInt("RoleID")),
                        rs.getBoolean("GetNotifications"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedBy")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateInfoUser(User user) {
        String sql = "UPDATE dbo.[User] SET FirstName = ?, LastName = ?, Phone = ?, UpdatedAt = GETDATE() WHERE ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getPhone());
            st.setInt(4, user.getID());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertNewUser(GoogleLogin user) {
        try {
            String sql = "INSERT INTO dbo.[User] (FirstName, LastName, Email, RoleID, CreatedAt, UpdatedAt) "
                    + "VALUES (?, ?, ?, ?, ?, GETDATE(), GETDATE());";
            PreparedStatement stm1 = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm1.setString(1, user.getGiven_name());
            stm1.setString(2, user.getFamily_name());
            stm1.setString(3, user.getEmail());
            stm1.setDouble(4, 0.0);
            stm1.setInt(5, 2);
            stm1.executeUpdate();

            ResultSet generateKey = stm1.getGeneratedKeys();
            int userID = -1;
            if (generateKey.next()) {
                userID = generateKey.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            GoogleLoginDAO ggld = new GoogleLoginDAO();
            ggld.insertGUser(user, userID);
        } catch (SQLException ex) {
            Logger.getLogger(GoogleLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT ID, FirstName, LastName, Email, Phone, RoleID, CreatedAt, UpdatedAt, IsDelete  FROM dbo.[user] WHERE Email = ?;\n"
                + "            \n"
                + "";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            // Kiểm tra nếu kết quả trả về có giá trị
            if (rs.next()) {
                // Lấy dữ liệu và khởi tạo đối tượng User
                RoleDAO roleDAO = new RoleDAO();  // Giả sử RoleDAO sẽ lấy Role từ database
                Role role = roleDAO.getRoleById(rs.getInt("RoleID"));  // Lấy role từ RoleID

                // Tạo đối tượng User từ kết quả truy vấn
                User user = new User(
                        rs.getInt("ID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        role, // Đối tượng Role
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        null, // Không có DeletedAt trong constructor, có thể thêm nếu cần
                        rs.getBoolean("IsDelete"),
                        0 // DeletedBy, nếu có thì thay số này
                );
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error in getUserByEmail: " + e.getMessage());
        }
        return null;  // Trả về null nếu không tìm thấy người dùng
    }

    // Thu - Iter 3
    public boolean checkAdmin(int userId) {
        try {
            String sql = "SELECT ID FROM dbo.[User] WHERE ID = ? AND RoleID = 1";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void updateByAdmin(String id, String firstname, String lastname, String email, String phone, String balance) {
        String sql = "UPDATE dbo.[User] SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, UpdatedAt = GETDATE() WHERE ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, balance);
            st.setString(6, id);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteUser(String id) {
        try {
            String sql = "UPDATE dbo.[User] SET IsDelete = 1, DeletedAt = GETDATE() WHERE ID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStatusByAdmin(String userId, boolean active, int adid) {
        try {
            String sql;
            if (active) {
                sql = "UPDATE dbo.[User] "
                        + "SET IsDelete = ?, "
                        + "DeletedBy = ?, "
                        + "DeletedAt = GETDATE() "
                        + "WHERE ID = ?;";
            } else {
                sql = "UPDATE dbo.[User] "
                        + "SET IsDelete = ?, "
                        + "DeletedBy = NULL, "
                        + "DeletedAt = NULL "
                        + "WHERE ID = ?;";
            }

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, active);

            if (active) {
                stm.setInt(2, adid);
                stm.setString(3, userId);
            } else {
                stm.setString(2, userId);
            }

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating status by admin: " + e.getMessage());
        }
    }

//    public User getUserbyEmail2(String email) {
//        String sql = "SELECT ID, FirstName, LastName, Email, Phone, RoleID, CreatedAt, UpdatedAt, DeletedAt, IsDelete, DeletedBy "
//                + "FROM dbo.[User] WHERE Email = ?";
//        try {
//            PreparedStatement st = connection.prepareStatement(sql);
//            st.setString(1, email);
//            ResultSet rs = st.executeQuery();
//            if (rs.next()) {
//                RoleDAO rdao = new RoleDAO();
//                Role role = rdao.getRoleById(rs.getInt("RoleID"));
//                return new User(
//                        rs.getInt("ID"),
//                        rs.getString("FirstName"),
//                        rs.getString("LastName"),
//                        rs.getString("Email"),
//                        rs.getString("Phone"),
//                        role,
//                        rs.getDate("CreatedAt"),
//                        rs.getDate("UpdatedAt"),
//                        rs.getDate("DeletedAt"),
//                        rs.getBoolean("IsDelete"),
//                        rs.getInt("DeletedBy")
//                );
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return null;
//    }

}
