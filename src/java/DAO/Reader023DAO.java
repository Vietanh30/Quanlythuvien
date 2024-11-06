package DAO;

import Model.Member023;
import Model.Reader023;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reader023DAO {

    private Connection connection;

    public Reader023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection(); // Kết nối cơ sở dữ liệu
    }

    Reader023DAO(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Phương thức kiểm tra sự tồn tại của email
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Member023 WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Trả về true nếu email đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi hoặc email không tồn tại
    }

    // Phương thức để đăng ký người dùng
    public boolean register(String name, String email, String password) {
        // Kiểm tra xem email đã tồn tại chưa
        if (isEmailExists(email)) {
            System.out.println("Email đã tồn tại. Vui lòng chọn email khác.");
            return false; // Không đăng ký nếu email đã tồn tại
        }

        String sql = "INSERT INTO Member023 (name, email, password, role, phone, birthdate, address) VALUES (?, ?, ?, 'reader', ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password); // Nên mã hóa mật khẩu trước khi lưu
            statement.setString(4, null); // Số điện thoại mặc định là null
            statement.setString(5, null); // Ngày sinh mặc định là null
            statement.setString(6, null); // Địa chỉ mặc định là null

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu có ít nhất 1 dòng được chèn
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    // Phương thức để đăng nhập người dùng
    public Member023 login(String email, String password) {
        String sql = "SELECT * FROM Member023 WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password); // Nên mã hóa mật khẩu trước khi so sánh

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Lấy thông tin người dùng từ kết quả truy vấn
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                String phone = resultSet.getString("phone");
                String birthdate = resultSet.getString("birthdate");
                String address = resultSet.getString("address");

                // Tạo và trả về đối tượng Member023
                Member023 member = new Member023(id, name, password, phone, email, birthdate, address, role);

                // Thêm thông tin độc giả với mã và lớp rỗng ngay sau khi đăng nhập thành công
                boolean isReaderAdded = addReader(id, "", ""); // Mã và lớp rỗng
                if (isReaderAdded) {
                    System.out.println("Thông tin độc giả đã được thêm thành công!");
                } else {
                    System.out.println("Không thể thêm độc giả.");
                }

                return member;
            } else {
                return null; // Trả về null nếu không tìm thấy người dùng
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    // Phương thức để thêm thông tin độc giả
    public boolean addReader(int memberId, String readerCode, String className) {
        String sql = "INSERT INTO Reader023 (Member023id, reader_code, class) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            statement.setString(2, readerCode); // Để rỗng
            statement.setString(3, className); // Để rỗng

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu có ít nhất 1 dòng được chèn
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    // Phương thức để lấy thông tin độc giả theo id
    public Reader023 getReaderById(int memberId) {
        String sql = "SELECT r.reader_code, r.class, m.* "
                + "FROM Reader023 r "
                + "JOIN Member023 m ON r.Member023id = m.id "
                + "WHERE r.Member023id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Lấy thông tin từ bảng Reader023
                String readerCode = resultSet.getString("reader_code");
                String className = resultSet.getString("class");

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

                // Tạo và trả về đối tượng Reader023
                return new Reader023(memberId, readerCode, className, member);
            } else {
                return null; // Trả về null nếu không tìm thấy độc giả
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    // Phương thức để cập nhật thông tin độc giả
    public boolean updateReader(int memberId, String readerCode, String className) {
        String sql = "UPDATE Reader023 SET reader_code = ?, class = ? WHERE Member023id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, readerCode);
            statement.setString(2, className);
            statement.setInt(3, memberId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất 1 dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    // Phương thức để cập nhật thông tin thành viên
    public boolean updateMember(int memberId, String name, String email, String phone, String birthdate, String address) {
        String sql = "UPDATE Member023 SET name = ?, email = ?, phone = ?, birthdate = ?, address = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, birthdate);
            statement.setString(5, address);
            statement.setInt(6, memberId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất 1 dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    // Phương thức để lấy tất cả độc giả
    public List<Reader023> getAllReaders() {
        List<Reader023> readers = new ArrayList<>();
        String sql = "SELECT * FROM Reader023";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Lấy thông tin từ bảng Reader023
                String readerCode = resultSet.getString("reader_code");
                String className = resultSet.getString("class");

                // Lấy thông tin từ bảng Member023
                int memberId = resultSet.getInt("Member023id");
                String name = getReaderById(memberId).getMember().getName();
                String email = getReaderById(memberId).getMember().getEmail();
                String password = getReaderById(memberId).getMember().getPassword();
                String phone = getReaderById(memberId).getMember().getPhone();
                String birthdate = getReaderById(memberId).getMember().getBirthdate();
                String address = getReaderById(memberId).getMember().getAddress();
                String role = getReaderById(memberId).getMember().getRole();

                // Tạo đối tượng Member023
                Member023 member = new Member023(memberId, name, password, phone, email, birthdate, address, role);

                // Tạo và thêm đối tượng Reader023 vào danh sách
                readers.add(new Reader023(memberId, readerCode, className, member));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers; // Trả về danh sách độc giả
    }

    public List<Reader023> searchReader(String readerCode) {
        List<Reader023> readers = new ArrayList<>();
        String sql = "SELECT r.reader_code, r.class, m.* "
                + "FROM Reader023 r "
                + "JOIN Member023 m ON r.Member023id = m.id "
                + "WHERE r.reader_code LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + readerCode + "%"); // Sử dụng LIKE để tìm kiếm
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Lấy thông tin từ bảng Reader023
                String rCode = resultSet.getString("reader_code");
                String className = resultSet.getString("class");

                // Lấy thông tin từ bảng Member023
                int memberId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String birthdate = resultSet.getString("birthdate");
                String address = resultSet.getString("address");
                String role = resultSet.getString("role");

                // Tạo đối tượng Member023
                Member023 member = new Member023(memberId, name, password, phone, email, birthdate, address, role);

                // Tạo và thêm đối tượng Reader023 vào danh sách
                readers.add(new Reader023(memberId, rCode, className, member));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers; // Trả về danh sách độc giả tìm được
    }

    public static void main(String[] args) {
        try {
            Reader023DAO readerDAO = new Reader023DAO();

            // Tìm kiếm độc giả theo mã
            String searchCode = "BD001"; // Thay đổi theo yêu cầu tìm kiếm
            List<Reader023> searchResults = readerDAO.getAllReaders();
            System.out.println("Kết quả tìm kiếm độc giả:");
            for (Reader023 reader : searchResults) {
                System.out.println("ID: " + reader.getMember().getId() + ", Mã: " + reader.getReaderCode() + ", Lớp: " + reader.getClassName());
                System.out.println("Name: " + reader.getMember().getName());
                System.out.println("Email: " + reader.getMember().getEmail());
                System.out.println("----------");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
