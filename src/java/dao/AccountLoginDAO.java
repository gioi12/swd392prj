package dao;

import entity.AccountLogin;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;

public class AccountLoginDAO extends DBContext {

    public AccountLogin getAccountByID(int userId) {
        AccountLogin a;
        String sql = "SELECT [ID], [UserID], [UserKey], [PassWord], [VerifyEmail], "
                + "[CreatedAt], [UpdatedAt], [DeletedAt], [IsDelete], [DeletedByID] "
                + "FROM [dbo].[authentication] WHERE [UserID] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                UserDAO u = new UserDAO();
                User user = u.getUserById(userId);
                a = new AccountLogin(rs.getInt("ID"),
                        user,
                        rs.getString("UserKey"),
                        rs.getString("PassWord"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedByID"));
                return a;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<AccountLogin> getAllAccount() {
        List<AccountLogin> accounts = new ArrayList<>();
        String sql = "SELECT [ID], [UserID], [UserKey], [PassWord], [VerifyEmail], "
                + "[CreatedAt], [UpdatedAt], [DeletedAt], [IsDelete], [DeletedByID] "
                + "FROM [dbo].[authentication]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UserDAO u = new UserDAO();
                User user = u.getUserById(rs.getInt("UserID"));
                AccountLogin account = new AccountLogin(
                        rs.getInt("ID"),
                        user,
                        rs.getString("UserKey"),
                        rs.getString("PassWord"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("DeletedByID")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }
        return accounts;
    }

    public void updateAccount(AccountLogin account) {
        String sql = "UPDATE [dbo].[authentication] "
                + "SET [PassWord] = ?, [UpdatedAt] = GETDATE() "
                + "WHERE [ID] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, account.getPassword());
            st.setInt(2, account.getID());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean checkUsernameExist(String username) {
        String sql = "SELECT [UserKey] FROM [dbo].[authentication] WHERE [UserKey] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkEmailExist(String email) {
        String sql = "SELECT [Email] FROM dbo.[user] WHERE [Email] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public void register(String username, String email, String password, String firstName, String lastName, String phone) {
        // SQL câu lệnh để insert vào bảng user
        String sql = "INSERT INTO [dbo].[user] ([Email], [RoleID], [FirstName], [LastName], [Phone], [CreatedAt], [UpdatedAt], [IsDelete]) "
                + "VALUES (?, 3, ?, ?, ?, GETDATE(), GETDATE(), 1);";

        try (PreparedStatement st = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, email);
            st.setString(2, firstName);   // FirstName
            st.setString(3, lastName);    // LastName
            st.setString(4, phone);       // Phone
            st.executeUpdate();

            // Get the ID of the recently inserted user
            try (ResultSet rs_1 = st.getGeneratedKeys()) {
                if (rs_1.next()) {
                    int userId = rs_1.getInt(1); // Getting the generated user ID

                    // SQL câu lệnh để insert vào bảng authentication
                    String sql_2 = "INSERT INTO [dbo].[authentication] "
                            + "([UserID], [UserKey], [PassWord], [CreatedAt], [UpdatedAt], [IsDelete]) "
                            + "VALUES (?, ?, ?, GETDATE(), GETDATE(), 1);";

                    try (PreparedStatement st_2 = connection.prepareStatement(sql_2)) {
                        st_2.setInt(1, userId);
                        st_2.setString(2, username);  // UserKey
                        st_2.setString(3, password);  // Password
                        st_2.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in register method: " + e.getMessage());
        }
    }

    public void changePass(int id, String newPass) {
        try {
            String sql = "UPDATE [dbo].[authentication] "
                    + "SET [PassWord] = ?, [UpdatedAt] = GETDATE() WHERE [UserID] = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, newPass);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void deleteAccount(String id) {
        try {
            String sql = "UPDATE [dbo].[authentication] "
                    + "SET [IsDelete] = 1, [DeletedAt] = GETDATE() WHERE [UserID] = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<AccountLogin> findUserByName(String name) {
        ArrayList<AccountLogin> listUserSearch = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[authentication] WHERE [UserKey] LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    int userId = rs.getInt("UserID");
                    UserDAO udao = new UserDAO();
                    User user = udao.getUserById(userId);
                    AccountLogin accountLogin = new AccountLogin(
                            id, user, rs.getString("UserKey"), rs.getString("PassWord"),
                            rs.getDate("CreatedAt"), rs.getDate("UpdatedAt"),
                            rs.getBoolean("IsDelete"), rs.getInt("DeletedByID"));
                    listUserSearch.add(accountLogin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUserSearch;
    }

    public void register2(String username, String email, String password) {
        String sql = "INSERT INTO [dbo].[user] ([Email], [RoleID], [CreatedAt], [UpdatedAt]) "
                + "VALUES (?, 2, GETDATE(), GETDATE());";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.executeUpdate();

            String sql_1 = "SELECT TOP 1 [ID] FROM [dbo].[user] ORDER BY [ID] DESC;";
            PreparedStatement st_1 = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st_1.executeQuery();

            if (rs_1.next()) {
                int userid = rs_1.getInt("ID");

                String sql_2 = "INSERT INTO [dbo].[authentication] "
                        + "([UserID], [UserKey], [PassWord], [CreatedAt], [UpdatedAt]) "
                        + "VALUES (?, ?, ?, GETDATE(), GETDATE());";

                PreparedStatement st_2 = connection.prepareStatement(sql_2);
                st_2.setInt(1, userid);
                st_2.setString(2, username);
                st_2.setString(3, password);
                st_2.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateLoginInfo(String id, String password) {
        try {
            String sql = "UPDATE [dbo].[authentication] "
                    + "SET [PassWord] = ?, [UpdatedAt] = GETDATE() "
                    + "WHERE [UserID] = ?;";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, id);
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void updateStatusRegister(String email, String userkey) {
        String sql = "UPDATE [dbo].[user] SET [IsDelete] = 0 WHERE [Email] = ?;";
        String sql2 = "UPDATE [dbo].[authentication] SET [IsDelete] = 0 WHERE [UserKey] = ?;";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.executeUpdate();

            PreparedStatement st2 = connection.prepareStatement(sql2);
            st2.setString(1, userkey);
            st2.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateLoginStatus(String userId, boolean active, int adid) {
        try {
            String sql;
            if (active) {
                sql = "UPDATE [dbo].[authentication] "
                        + "SET [IsDelete] = ?, [DeletedAt] = GETDATE(), [DeletedByID] = ? "
                        + "WHERE [UserID] = ?;";
            } else {
                sql = "UPDATE [dbo].[authentication] "
                        + "SET [IsDelete] = ?, [DeletedAt] = NULL, [DeletedByID] = NULL "
                        + "WHERE [UserID] = ?;";
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        AccountLoginDAO acc = new AccountLoginDAO();
        acc.changePass(1, "abcd");
    }
}
