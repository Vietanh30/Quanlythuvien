package DAO;

import Model.ReturnOrder023; // Đảm bảo bạn có lớp ReturnOrder
import Model.Reader023; // Đảm bảo bạn có lớp Reader023
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnOrder023DAO {

    private Connection connection;
    private Reader023DAO readerDAO;
    private Document023DAO documentDAO;

    public ReturnOrder023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection();
        readerDAO = new Reader023DAO();
        documentDAO = new Document023DAO();
    }

    ReturnOrder023DAO(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<ReturnOrder023> getAllReturnOrder() throws SQLException {
        List<ReturnOrder023> returnOrders = new ArrayList<>();
        String sql = "SELECT * FROM ReturnOrder023"; // Truy vấn lấy tất cả phiếu trả

        try (PreparedStatement pstm = connection.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                // Lấy dữ liệu của phiếu trả
                int id = rs.getInt("id");
                String returnDate = rs.getString("return_date");
                int fine = rs.getInt("fine");
                int readerId = rs.getInt("Reader023id");
                int librarianId = rs.getInt("Librarian023id");

                // Lấy thông tin độc giả
                Reader023 reader = readerDAO.getReaderById(readerId);
                // Tạo đối tượng ReturnOrder023
                ReturnOrder023 returnOrder = new ReturnOrder023(id, returnDate, fine, readerId, librarianId, reader);
                returnOrders.add(returnOrder);
            }
        }
        return returnOrders; // Trả về danh sách phiếu trả
    }

    public void addReturnOrder(ReturnOrder023 returnOrder) throws SQLException {
        String sql = "INSERT INTO ReturnOrder023 (return_date, fine, Reader023id, Librarian023id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, returnOrder.getReturnDate());
            stmt.setInt(2, returnOrder.getFine());
            stmt.setInt(3, returnOrder.getReaderId());
            stmt.setInt(4, returnOrder.getLibrarianId());
            stmt.executeUpdate();
        }
    }

    public void updateReturnOrder(ReturnOrder023 returnOrder) throws SQLException {
        String sql = "UPDATE ReturnOrder023 SET return_date = ?, fine = ?, Reader023id = ?, Librarian023id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, returnOrder.getReturnDate());
            stmt.setInt(2, returnOrder.getFine());
            stmt.setInt(3, returnOrder.getReaderId());
            stmt.setInt(4, returnOrder.getLibrarianId());
            stmt.setInt(5, returnOrder.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteReturnOrder(int id) throws SQLException {
        String sql = "DELETE FROM ReturnOrder023 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void updateDocumentReturn(int documentId, int quantity) throws SQLException {
        String sql = "UPDATE Document023 SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, documentId);
            stmt.executeUpdate();
        }
    }

    public ReturnOrder023 getReturnOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM ReturnOrder023 WHERE id = ?";
        ReturnOrder023 returnOrder = null;

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Lấy dữ liệu của phiếu trả
                    String returnDate = rs.getString("return_date");
                    int fine = rs.getInt("fine");
                    int readerId = rs.getInt("Reader023id");
                    int librarianId = rs.getInt("Librarian023id");

                    // Lấy thông tin độc giả
                    Reader023 reader = readerDAO.getReaderById(readerId);
                    // Tạo đối tượng ReturnOrder023
                    returnOrder = new ReturnOrder023(id, returnDate, fine, readerId, librarianId, reader);
                }
            }
        }
        return returnOrder; // Trả về phiếu trả tìm thấy hoặc null nếu không có
    }
// Phương thức in thông tin phiếu trả

    private static void printReturnOrderInfo(ReturnOrder023 returnOrder) {
        System.out.println("ID: " + returnOrder.getId());
        System.out.println("Ngày trả: " + returnOrder.getReturnDate());
        System.out.println("Tiền phạt: " + returnOrder.getFine());
        System.out.println("ID độc giả: " + returnOrder.getReaderId());
        if (returnOrder.getReader() != null) {
            System.out.println("Mã độc giả: " + returnOrder.getReader().getReaderCode());
            System.out.println("Lớp độc giả: " + returnOrder.getReader().getClassName());
        }
        if (returnOrder.getReader() != null) {
            System.out.println("Tên thành viên: " + returnOrder.getReader().getMember().getName());
            System.out.println("Email thành viên: " + returnOrder.getReader().getMember().getEmail());
        }
        System.out.println("ID thủ thư: " + returnOrder.getLibrarianId());
        System.out.println("----------");
    }

    public static void main(String[] args) {
        try {
            ReturnOrder023DAO returnOrderDAO = new ReturnOrder023DAO();

            // Thêm một phiếu trả mới
            ReturnOrder023 newReturnOrder = new ReturnOrder023();
            newReturnOrder.setReturnDate("2024-11-05");
            newReturnOrder.setFine(0);
            newReturnOrder.setReaderId(1);
            newReturnOrder.setLibrarianId(2);
            returnOrderDAO.addReturnOrder(newReturnOrder);
            System.out.println("Đã thêm phiếu trả thành công!");

            // Cập nhật phiếu trả
//            newReturnOrder.setFine(10); // Cập nhật tiền phạt
//            returnOrderDAO.updateReturnOrder(newReturnOrder);
//            System.out.println("Đã cập nhật phiếu trả thành công!");

            // Kiểm tra phương thức getAllReturnOrder
            System.out.println("Tất cả các phiếu trả:");
            List<ReturnOrder023> returnOrders = returnOrderDAO.getAllReturnOrder();
            for (ReturnOrder023 returnOrder : returnOrders) {
                printReturnOrderInfo(returnOrder);
            }

            // Thử nghiệm phương thức getReturnOrderById
            System.out.println("Kiểm tra phiếu trả với ID 1:");
            ReturnOrder023 foundReturnOrder = returnOrderDAO.getReturnOrderById(1);
            if (foundReturnOrder != null) {
                printReturnOrderInfo(foundReturnOrder);
            } else {
                System.out.println("Không tìm thấy phiếu trả với ID: 1");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy lớp: " + e.getMessage());
        }
    }
}
