package DAO;

import Model.BorrowingOrder023;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingOrder023DAO {

    private Connection connection;

    public BorrowingOrder023DAO(Connection connection) {
        this.connection = connection;
    }

    public BorrowingOrder023DAO() {
    }

    public int addBorrowingOrder(BorrowingOrder023 order) throws SQLException {
        String sql = "INSERT INTO BorrowingOrder023 (return_date, borrow_date, Reader023id, Librarian023id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, order.getReturnDate());
            stmt.setString(2, order.getBorrowDate());
            stmt.setInt(3, order.getReaderId());
            stmt.setInt(4, order.getLibrarianId());
            stmt.executeUpdate();

            // Lấy ID của bản ghi mới
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // trả về ID mới
                } else {
                    throw new SQLException("Không thể lấy ID của phiếu mượn mới.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Không thể thêm phiếu mượn: " + e.getMessage(), e);
        }
    }

    public BorrowingOrder023 getBorrowingOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM BorrowingOrder023 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BorrowingOrder023(
                        rs.getInt("id"),
                        rs.getString("return_date"),
                        rs.getString("borrow_date"),
                        rs.getInt("Reader023id"),
                        rs.getInt("Librarian023id")
                );
            } else {
                throw new SQLException("Không tìm thấy phiếu mượn với ID: " + id);
            }
        }
    }

    public void updateBorrowingOrder(BorrowingOrder023 order) throws SQLException {
        String sql = "UPDATE BorrowingOrder023 SET return_date = ?, borrow_date = ?, Reader023id = ?, Librarian023id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, order.getReturnDate());
            stmt.setString(2, order.getBorrowDate());
            stmt.setInt(3, order.getReaderId());
            stmt.setInt(4, order.getLibrarianId());
            stmt.setInt(5, order.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Không thể cập nhật phiếu mượn: " + e.getMessage(), e);
        }
    }

    public void deleteBorrowingOrder(int id) throws SQLException {
        String sql = "DELETE FROM BorrowingOrder023 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Không thể xóa phiếu mượn: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        try {
            // Kết nối tới cơ sở dữ liệu
            Connection connection = ConnectDatabase.getMySQLConnection();
            BorrowingOrder023DAO borrowingOrderDAO = new BorrowingOrder023DAO(connection);

            // Tạo một phiếu mượn mới
            BorrowingOrder023 newOrder = new BorrowingOrder023();
            newOrder.setReturnDate("2024-11-15");
            newOrder.setBorrowDate("2024-11-01");
            newOrder.setReaderId(1); // ID độc giả
            newOrder.setLibrarianId(2); // ID thủ thư

//             Thêm phiếu mượn vào cơ sở dữ liệu và lấy ID mới
            int generatedId = borrowingOrderDAO.addBorrowingOrder(newOrder);
            newOrder.setId(generatedId); // Thiết lập ID cho đối tượng
            System.out.println("Đã thêm phiếu mượn thành công với ID: " + newOrder.getId());
            // Lấy phiếu mượn vừa tạo
            BorrowingOrder023 existingOrder = borrowingOrderDAO.getBorrowingOrderById(1);
            System.out.println("Đã lấy phiếu mượn: " + existingOrder);

            // Cập nhật thông tin phiếu mượn
//            existingOrder.setReturnDate("2024-11-20");
//            borrowingOrderDAO.updateBorrowingOrder(existingOrder);
            System.out.println("Đã cập nhật phiếu mượn thành công!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
