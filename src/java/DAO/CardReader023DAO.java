package DAO;

import Model.ReaderCard023;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardReader023DAO {

    private Connection connection;

    public CardReader023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection();
    }

    // Phương thức để kiểm tra thẻ bạn đọc có tồn tại không
    public boolean cardExists(int readerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ReaderCard023 WHERE Reader023id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, readerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // Phương thức để tạo mã thẻ cố định
    private String generateCardCode(int readerId) throws SQLException {
        // Tạo mã thẻ theo định dạng A0001, A0002, ... dựa trên ID độc giả
        return "A" + String.format("%04d", readerId);
    }

    // Phương thức để cập nhật thẻ bạn đọc
    public boolean updateCardReader(int readerId) {
        String sqlCard = "UPDATE ReaderCard023 SET issue_date = ?, expiration_date = ? WHERE Reader023id = ?";

        try {
            // Sử dụng mã thẻ cố định
            String cardCode = generateCardCode(readerId);

            // Cập nhật thông tin thẻ bạn đọc
            try (PreparedStatement cardStatement = connection.prepareStatement(sqlCard)) {
                cardStatement.setDate(1, new Date(System.currentTimeMillis())); // Ngày phát hành là ngày hiện tại
                cardStatement.setDate(2, new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000 * 4)); // Ngày hết hạn là bốn năm sau
                cardStatement.setInt(3, readerId); // Sử dụng ID độc giả đã tạo

                return cardStatement.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Thất bại
        }
    }

    // Phương thức để thêm thẻ bạn đọc mới
    public boolean createCardReader(int readerId) {
        String sqlCard = "INSERT INTO ReaderCard023 (code, issue_date, expiration_date, Reader023id) VALUES (?, ?, ?, ?)";

        try {
            // Tạo mã thẻ cố định
            String cardCode = generateCardCode(readerId);
            if (cardCode == null) {
                throw new SQLException("Không thể tạo mã thẻ.");
            }

            // Lưu thông tin thẻ bạn đọc
            try (PreparedStatement cardStatement = connection.prepareStatement(sqlCard)) {
                cardStatement.setString(1, cardCode); // Sử dụng mã thẻ cố định
                cardStatement.setDate(2, new Date(System.currentTimeMillis())); // Ngày phát hành là ngày hiện tại
                cardStatement.setDate(3, new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000 * 4)); // Ngày hết hạn là bốn năm sau
                cardStatement.setInt(4, readerId); // Sử dụng ID độc giả đã tạo

                return cardStatement.executeUpdate() > 0; // Trả về true nếu thêm thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Thất bại
        }
    }

    // Phương thức để tạo hoặc cập nhật thẻ bạn đọc
    public boolean createOrUpdateCardReader(int readerId) throws SQLException {
        if (cardExists(readerId)) {
            System.out.println("Thẻ đã tồn tại, cập nhật thông tin...");
            return updateCardReader(readerId);
        } else {
            System.out.println("Thẻ chưa tồn tại, tạo mới...");
            return createCardReader(readerId);
        }
    }

    // Phương thức để lấy tất cả thẻ bạn đọc
    public List<ReaderCard023> getAllCards() {
        List<ReaderCard023> cards = new ArrayList<>();
        String sql = "SELECT * FROM ReaderCard023"; // Đảm bảo tên bảng đúng

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ReaderCard023 card = new ReaderCard023();
                card.setId(resultSet.getInt("id")); // Lấy ID thẻ
                card.setCode(resultSet.getString("code"));
                card.setIssueDate(resultSet.getDate("issue_date"));
                card.setExpirationDate(resultSet.getDate("expiration_date"));
                card.setReaderId(resultSet.getInt("Reader023id")); // Lấy ID độc giả
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
        return cards; // Trả về danh sách thẻ
    }

    // Phương thức để lấy thẻ bạn đọc theo ID
    public ReaderCard023 getCardById(int id) {
        ReaderCard023 card = null;
        String sql = "SELECT * FROM ReaderCard023 WHERE id = ?"; // Sử dụng ID để tìm kiếm

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id); // Chuyển đổi ID thành số nguyên
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                card = new ReaderCard023();
                card.setId(resultSet.getInt("id")); // Lấy ID thẻ
                card.setCode(resultSet.getString("code"));
                card.setIssueDate(resultSet.getDate("issue_date"));
                card.setExpirationDate(resultSet.getDate("expiration_date"));
                card.setReaderId(resultSet.getInt("Reader023id")); // Lấy ID độc giả
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
        return card; // Trả về thẻ nếu tìm thấy, hoặc null
    }

    public static void main(String[] args) {
        try {
            CardReader023DAO cardReaderDAO = new CardReader023DAO();
            int readerId = 1; // ID độc giả cần kiểm tra
            cardReaderDAO.createOrUpdateCardReader(readerId);
            // Hiển thị thông tin tất cả thẻ bạn đọc
            List<ReaderCard023> cards = cardReaderDAO.getAllCards();
            for (ReaderCard023 card : cards) {
                System.out.println("ID: " + card.getId()
                        + ", Mã thẻ: " + card.getCode()
                        + ", Ngày phát hành: " + card.getIssueDate()
                        + ", Ngày hết hạn: " + card.getExpirationDate()
                        + ", ID độc giả: " + card.getReaderId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}