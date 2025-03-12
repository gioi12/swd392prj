package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DBProjectSWP391_V1;encrypt=false;trustServerCertificate=true";
    private static final String DB_USER = "sa";  // Thay đổi theo user SQL Server của bạn
    private static final String DB_PASSWORD = "Long2002@";  // Thay đổi theo password của bạn

    protected static Connection connection;

    public DBContext() {
        try {
            // Load SQL Server JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Kết nối đến cơ sở dữ liệu SQL Server
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Kết nối SQL Server thành công!");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Lỗi kết nối SQL Server", ex);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            new DBContext(); 
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đóng kết nối SQL Server thành công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Lỗi khi đóng kết nối", ex);
        }
    }
}
