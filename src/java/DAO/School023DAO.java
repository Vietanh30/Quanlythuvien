package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.School023; // Đảm bảo bạn có một lớp School023 để lưu trữ thông tin trường

public class School023DAO {

    private Connection connection;

    public School023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection();
    }

    public List<School023> getAllSchool() throws SQLException {
        List<School023> schools = new ArrayList<>();
        String sql = "SELECT * FROM School023"; // Truy vấn lấy tất cả trường

        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và tạo đối tượng School023
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String description = rs.getString("description");

                School023 school = new School023(id, name, address, description);
                schools.add(school);
            }
        }
        return schools; // Trả về danh sách trường
    }

    // Thêm phương thức getSchoolById
    public School023 getSchoolById(int id) throws SQLException {
        School023 school = null;
        String sql = "SELECT * FROM School023 WHERE id = ?"; // Truy vấn lấy trường theo ID

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Gán giá trị ID vào truy vấn
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Lấy dữ liệu từ ResultSet và tạo đối tượng School023
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String description = rs.getString("description");

                    school = new School023(id, name, address, description);
                }
            }
        }
        return school; // Trả về trường hoặc null nếu không tìm thấy
    }

    public static void main(String[] args) {
        try {
            // Tạo một đối tượng School023DAO
            School023DAO schoolDAO = new School023DAO();

            // Lấy danh sách tất cả các trường
            List<School023> schools = schoolDAO.getAllSchool();

            // In thông tin của các trường
            for (School023 school : schools) {
                System.out.println("ID: " + school.getId());
                System.out.println("Name: " + school.getName());
                System.out.println("Address: " + school.getAddress());
                System.out.println("Description: " + school.getDescription());
                System.out.println("----------");
            }

            // Kiểm tra phương thức getSchoolById
            int testId = 1; // Thay đổi ID này để kiểm tra trường cụ thể
            School023 schoolById = schoolDAO.getSchoolById(testId);
            if (schoolById != null) {
                System.out.println("Thông tin trường với ID " + testId + ":");
                System.out.println("ID: " + schoolById.getId());
                System.out.println("Name: " + schoolById.getName());
                System.out.println("Address: " + schoolById.getAddress());
                System.out.println("Description: " + schoolById.getDescription());
            } else {
                System.out.println("Không tìm thấy trường với ID " + testId);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class Not Found Exception: " + e.getMessage());
        }
    }
}