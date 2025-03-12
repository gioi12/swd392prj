/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.GoogleLogin;
import entity.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleLoginDAO extends DBContext {

    public GoogleLogin getByGoogleID(String id) {
        GoogleLogin gl = null;
        try {
            String sql = "Select U.ID, A.UserKey, U.Email, A.VerifyEmail, U.CreatedAt, U.UpdatedAt, U.DeletedAt, U.IsDelete, U.DeletedBy\n"
                    + "From User U\n"
                    + "INNER JOIN Authentication A ON A.UserID = U.ID\n"
                    + "WHERE A.UserKey = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                UserDAO ud = new UserDAO();
                gl = new GoogleLogin();
                gl.setId(rs.getString("A.UserKey"));
                gl.setEmail(rs.getString("U.Email"));
                gl.setVerified_email(rs.getBoolean("A.VerifyEmail"));
                gl.setUser(ud.getUserById(rs.getInt("U.ID")));
                gl.setCreatedAt(rs.getDate("U.CreatedAt"));
                gl.setUpdatedAt(rs.getDate("U.UpdatedAt"));
                gl.setDeletedAt(rs.getDate("U.DeletedAt"));
                gl.setIsDelete(rs.getBoolean("U.IsDelete"));
                gl.setDeletedByID(rs.getInt("U.DeletedBy"));
                return gl;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GoogleLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertGUser(GoogleLogin user, int userID) {
        try {
            String sql = "INSERT INTO [dbo].[authentication] "
                    + "([UserID], [UserKey], [VerifyEmail], [CreatedAt], [UpdatedAt]) "
                    + "VALUES (?, ?, ?, GETDATE(), GETDATE())";
            PreparedStatement stm2 = connection.prepareStatement(sql);
            stm2.setInt(1, userID);
            stm2.setString(2, user.getId());
            stm2.setBoolean(3, user.isVerified_email());
            stm2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoogleLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GoogleLogin getGoogleLoginByUid(int userId) {
        GoogleLogin ggl = null;
        String sql = "SELECT A.ID, A.UserID, A.UserKey, A.VerifyEmail, A.CreatedAt, A.UpdatedAt, A.DeletedAt, A.IsDelete, A.DeletedByID "
                + "FROM [Authentication] A "
                + "WHERE A.UserID = ?;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                UserDAO u = new UserDAO();
                ggl = new GoogleLogin(
                        rs.getString("UserKey"),
                        null,
                        rs.getBoolean("VerifyEmail"),
                        null,
                        null,
                        rs.getDate("CreatedAt"),
                        rs.getDate("UpdatedAt"),
                        rs.getDate("DeletedAt"),
                        rs.getBoolean("IsDelete"),
                        rs.getInt("UserID"),
                        u.getUserById(rs.getInt("UserID"))
                );
                return ggl;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        GoogleLoginDAO g = new GoogleLoginDAO();
        GoogleLogin a = g.getByGoogleID("110251263939657236132");
        System.out.println(a.toString());
    }
}
