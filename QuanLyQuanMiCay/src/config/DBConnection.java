package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    private static boolean driverLoaded;
    private static boolean connectionLogged;

    static {
        String host = Config.get("db.host", "localhost");
        String port = Config.get("db.port", "3306");
        String name = Config.get("db.name", "quanly_quan_mi_cay");
        String charset = Config.get("db.charset", "utf8");
        URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&characterEncoding=%s", host, port, name, charset);
        USER = Config.get("db.user", "root");
        PASSWORD = Config.get("db.password", "");
    }

    public static Connection getConnection() {
        try {
            if (!driverLoaded) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                driverLoaded = true;
            }
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connectionLogged) {
                System.out.println("Ket noi database thanh cong!");
                connectionLogged = true;
            }
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