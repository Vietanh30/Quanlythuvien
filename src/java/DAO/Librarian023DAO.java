package DAO;

import Model.Librarian023;
import Model.Member023;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Librarian023DAO {

    private Connection connection;

    public Librarian023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection(); // Kết nối cơ sở dữ liệu
    }

    // Phương thức để lấy tất cả thủ thư
    public List<Librarian023> getAllLibrarians() throws SQLException {
        List<Librarian023> librarians = new ArrayList<>();
        String sql = "SELECT l.Member023id, l.position, m.* FROM Librarian023 l JOIN Member023 m ON l.Member023id = m.id";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Lấy thông tin từ bảng Librarian023
                int memberId = resultSet.getInt("Member023id");
                String position = resultSet.getString("position");

                // Lấy thông tin từ bảng Member023
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String birthdate = resultSet.getString("birthdate");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");

                // Tạo đối tượng Member023
                Member023 member = new Member023(memberId, name, password, phone, email, birthdate, address, role);

                // Tạo đối tượng Librarian023
                Librarian023 librarian = new Librarian023(memberId, position, member);
                librarians.add(librarian);
            }
        }
        return librarians; // Trả về danh sách thủ thư
    }

    // Phương thức để lấy thông tin thủ thư theo ID
    public Librarian023 getLibrarianById(int id) throws SQLException {
        String sql = "SELECT l.Member023id, l.position, m.* FROM Librarian023 l JOIN Member023 m ON l.Member023id = m.id WHERE l.Member023id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Lấy thông tin từ bảng Librarian023
                int memberId = resultSet.getInt("Member023id");
                String position = resultSet.getString("position");

                // Lấy thông tin từ bảng Member023
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String birthdate = resultSet.getString("birthdate");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");

                // Tạo đối tượng Member023
                Member023 member = new Member023(memberId, name, password, phone, email, birthdate, address, role);

                // Tạo đối tượng Librarian023
                return new Librarian023(memberId, position, member);
            } else {
                return null; // Trả về null nếu không tìm thấy thủ thư
            }
        }
    }

    public static void main(String[] args) {
        try {
            Librarian023DAO librarianDAO = new Librarian023DAO();

            // Lấy tất cả thủ thư
            System.out.println("Danh sách thủ thư:");
            List<Librarian023> librarians = librarianDAO.getAllLibrarians();
            for (Librarian023 librarian : librarians) {
                System.out.println("Member ID: " + librarian.getMemberId() + ", Position: " + librarian.getPosition());
                System.out.println("Member Name: " + librarian.getMember().getName());
                System.out.println("----------");
            }

            // Lấy thông tin thủ thư theo ID
            int testLibrarianId = 2; // Thay đổi thành ID hợp lệ trong cơ sở dữ liệu của bạn
            Librarian023 librarian = librarianDAO.getLibrarianById(testLibrarianId);
            if (librarian != null) {
                System.out.println("Thông tin thủ thư với ID " + testLibrarianId + ":");
                System.out.println("Position: " + librarian.getPosition());
                System.out.println("Member Name: " + librarian.getMember().getName());
                System.out.println("Member Name: " + librarian.getMember().getEmail());

            } else {
                System.out.println("Không tìm thấy thủ thư với ID: " + testLibrarianId);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
