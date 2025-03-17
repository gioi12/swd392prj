package org.dal;

import org.entity.Account;
import org.entity.Feature;
import org.entity.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDBContext extends DBContext{
    public ArrayList<Role> getRoles(String username) {
        String sql = "SELECT r.id AS rid, r.name AS rname, f.id AS fid, f.name AS fname, f.url FROM Account a " +
                "JOIN AccountRole ar ON ar.account_id = a.id " +
                "JOIN Role r ON ar.role_id = r.id " +
                "JOIN RoleFeature rf ON rf.role_id = r.id " +
                "JOIN Feature f ON f.id = rf.feature_id " +
                "WHERE a.username = ? " +
                "ORDER BY r.id, f.id ASC";

        PreparedStatement stm = null;
        ArrayList<Role> roles = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int roleId = rs.getInt("rid");
                String roleName = rs.getString("rname");
                int featureId = rs.getInt("fid");
                String featureName = rs.getString("fname");
                String featureUrl = rs.getString("url");

                Role role = new Role();
                role.setId(roleId);
                role.setName(roleName);
                role.setFeatures(new ArrayList<>());

                Feature feature = new Feature();
                feature.setId(featureId);
                feature.setName(featureName);
                feature.setUrl(featureUrl);

                role.getFeatures().add(feature);
                roles.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) stm.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return roles;
    }
    public Account Login(String user , String pass){
        Account acc = new Account();
        String sql = "Select * From Account Where userName = ? AND password = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user);
            stm.setString(2, pass);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                acc.setUsername(rs.getString("name"));
                acc.setId(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return acc;
    }


}
