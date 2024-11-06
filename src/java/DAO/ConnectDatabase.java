/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/library_pttk?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "123456";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbURL, username, password);
        if (conn != null) {
            System.out.println("Kết nối thành công");
            return conn;
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // Lấy kết nối đến cơ sở dữ liệu
            Connection conn = ConnectDatabase.getMySQLConnection();

            if (conn != null) {
                System.out.println("Kết nối thành công đến cơ sở dữ liệu.");
                // Đóng kết nối
                conn.close();
                System.out.println("Đã đóng kết nối.");
            } else {
                System.out.println("Kết nối không thành công.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Lỗi khi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
