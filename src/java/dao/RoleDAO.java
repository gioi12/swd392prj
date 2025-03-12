package dao;

import entity.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDAO extends DBContext {

    public Role getRoleById(int roleId) {
    Role r = null;
    String sql = "SELECT [ID], [RoleName], [Description], [CreatedAt], [UpdatedAt], [DeletedAt], [IsDelete] "
                + "FROM [dbo].[userrole] "
                + "WHERE [ID] = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, roleId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            r = new Role(rs.getInt("ID"),
                    rs.getString("RoleName"),
                    rs.getString("Description"),
                    rs.getDate("CreatedAt"),
                    rs.getDate("UpdatedAt"),
                    rs.getDate("DeletedAt"),
                    rs.getBoolean("IsDelete"));
        }
    } catch (Exception e) {
        System.out.println("Error in getRoleById: " + e.getMessage());
    }
    return r;
}


    public Role getUserRoleById(int id) {
    String sql = "SELECT [ID], [RoleName], [Description], [CreatedAt], [UpdatedAt], [DeletedAt], [IsDelete] "
                + "FROM [dbo].[userrole] "
                + "WHERE [ID] = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            Role userRole = new Role(rs.getInt("ID"),
                    rs.getString("RoleName"),
                    rs.getString("Description"),
                    rs.getDate("CreatedAt"),
                    rs.getDate("UpdatedAt"),
                    rs.getDate("DeletedAt"),
                    rs.getBoolean("IsDelete"));
            return userRole;
        }
    } catch (Exception e) {
        System.out.println("Error in getUserRoleById: " + e.getMessage());
    }
    return null;
}

}
