package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quanly_quan_mi_cay?useSSL=false&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    // Không lưu connection static nữa, mỗi lần gọi đều mới
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Ket noi database thanh cong!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("LOI: Khong tim thay driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("LOI ket noi: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}