package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Thông tin kết nối
    private static final String URL = "jdbc:mysql://localhost:3306/qlThuChi?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    // Hàm dùng chung lấy Connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
