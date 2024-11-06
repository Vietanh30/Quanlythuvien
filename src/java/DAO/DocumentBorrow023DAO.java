package DAO;

import Model.BorrowingOrder023;
import Model.Document023;
import Model.DocumentBorrowed023;
import Model.ReturnOrder023;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentBorrow023DAO {

    private Connection connection;

    public DocumentBorrow023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection();
    }

    // Phương thức lấy tất cả tài liệu đã mượn theo ID của độc giả
    public List<DocumentBorrowed023> getAllBorrowingByReaderId(int readerId) {
        List<DocumentBorrowed023> borrowedDocuments = new ArrayList<>();
        String sql = "SELECT db.id AS document_borrowed_id, db.quantity, d.id AS document_id, d.name, d.author, "
                + "d.publication_date, d.quantity AS total_quantity, d.stock, "
                + "bo.id AS borrowing_order_id "
                + "FROM DocumentBorrowed023 db "
                + "JOIN BorrowingOrder023 bo ON db.BorrowingOrder023id = bo.id "
                + "JOIN Document023 d ON db.Document023id = d.id "
                + "WHERE bo.Reader023id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, readerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Document023 document = new Document023();
                document.setId(resultSet.getInt("document_id"));
                document.setName(resultSet.getString("name"));
                document.setAuthor(resultSet.getString("author"));
                document.setPublicationDate(resultSet.getString("publication_date"));
                document.setQuantity(resultSet.getInt("total_quantity"));
                document.setStock(resultSet.getInt("stock"));

                DocumentBorrowed023 borrowedDocument = new DocumentBorrowed023();
                borrowedDocument.setId(resultSet.getInt("document_borrowed_id"));
                borrowedDocument.setQuantity(resultSet.getInt("quantity"));
                borrowedDocument.setDocument(document);

                // Lấy BorrowingOrder023 từ DAO
                int borrowingOrderId = resultSet.getInt("borrowing_order_id");
                BorrowingOrder023DAO borrowingOrderDAO = new BorrowingOrder023DAO(connection);
                BorrowingOrder023 borrowingOrder = borrowingOrderDAO.getBorrowingOrderById(borrowingOrderId);
                borrowedDocument.setBorrowingOrder(borrowingOrder);

                borrowedDocuments.add(borrowedDocument);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowedDocuments;
    }

    public List<DocumentBorrowed023> getAllReturningByReaderId(int readerId) throws ClassNotFoundException {
        List<DocumentBorrowed023> returnedDocuments = new ArrayList<>();
        String sql = "SELECT db.id AS document_borrowed_id, db.quantity, d.id AS document_id, d.name, d.author, "
                + "d.publication_date, d.quantity AS total_quantity, d.stock, "
                + "ro.id AS return_order_id "
                + "FROM DocumentBorrowed023 db "
                + "JOIN ReturnOrder023 ro ON db.ReturnOrder023id = ro.id "
                + "JOIN Document023 d ON db.Document023id = d.id "
                + "JOIN BorrowingOrder023 bo ON db.BorrowingOrder023id = bo.id "
                + "WHERE bo.Reader023id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, readerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Document023 document = new Document023();
                document.setId(resultSet.getInt("document_id"));
                document.setName(resultSet.getString("name"));
                document.setAuthor(resultSet.getString("author"));
                document.setPublicationDate(resultSet.getString("publication_date"));
                document.setQuantity(resultSet.getInt("total_quantity"));
                document.setStock(resultSet.getInt("stock"));

                DocumentBorrowed023 returnedDocument = new DocumentBorrowed023();
                returnedDocument.setId(resultSet.getInt("document_borrowed_id"));
                returnedDocument.setQuantity(resultSet.getInt("quantity"));
                returnedDocument.setDocument(document);

                int returnOrderId = resultSet.getInt("return_order_id");
                ReturnOrder023DAO returnOrderDAO = new ReturnOrder023DAO();
                ReturnOrder023 returnOrder = returnOrderDAO.getReturnOrderById(returnOrderId);
                returnedDocument.setReturnOrder(returnOrder);

                returnedDocuments.add(returnedDocument);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedDocuments;
    }

    public void addDocumentBorrowed(DocumentBorrowed023 documentBorrowed) throws SQLException {
        // Kiểm tra tồn kho trước khi thêm tài liệu mượn
        if (documentBorrowed.getQuantity() > documentBorrowed.getDocument().getStock()) {
            throw new SQLException("Không đủ tài liệu trong kho để mượn.");
        }

        // Kiểm tra xem tài liệu đã được mượn trong cùng phiếu mượn chưa
        String checkSql = "SELECT quantity FROM DocumentBorrowed023 WHERE Document023id = ? AND BorrowingOrder023id = ?";
        int existingQuantity = 0;

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, documentBorrowed.getDocument().getId());
            checkStmt.setInt(2, documentBorrowed.getBorrowingOrder().getId());

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                existingQuantity = rs.getInt("quantity");
            }
        }

        if (existingQuantity > 0) {
            // Tài liệu đã được mượn trong phiếu này, cập nhật số lượng
            String updateSql = "UPDATE DocumentBorrowed023 SET quantity = quantity + ? WHERE Document023id = ? AND BorrowingOrder023id = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setInt(1, documentBorrowed.getQuantity());
                updateStmt.setInt(2, documentBorrowed.getDocument().getId());
                updateStmt.setInt(3, documentBorrowed.getBorrowingOrder().getId());
                updateStmt.executeUpdate();
            }
        } else {
            // Tài liệu chưa được mượn, thêm mới
            String insertSql = "INSERT INTO DocumentBorrowed023 (quantity, Document023id, BorrowingOrder023id) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                insertStmt.setInt(1, documentBorrowed.getQuantity());
                insertStmt.setInt(2, documentBorrowed.getDocument().getId());
                insertStmt.setInt(3, documentBorrowed.getBorrowingOrder().getId());
                insertStmt.executeUpdate();
            }
        }

        // Giảm số lượng tồn kho sau khi thêm hoặc cập nhật tài liệu đã mượn
        updateDocumentStock(documentBorrowed.getDocument().getId(), documentBorrowed.getQuantity());
    }

    public void addDocumentsToReturnOrder(List<DocumentBorrowed023> documentsToReturn) throws SQLException {
        for (DocumentBorrowed023 documentBorrowed : documentsToReturn) {
            int documentId = documentBorrowed.getDocument().getId();
            int borrowingOrderId = documentBorrowed.getBorrowingOrder().getId();
            int returnQuantity = documentBorrowed.getQuantity();

            // Check the currently borrowed quantity
            String checkSql = "SELECT quantity FROM DocumentBorrowed023 WHERE Document023id = ? AND BorrowingOrder023id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, documentId);
                checkStmt.setInt(2, borrowingOrderId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int borrowedQuantity = rs.getInt("quantity");

                    // Validate return quantity
                    if (returnQuantity > borrowedQuantity) {
                        throw new SQLException("Return quantity exceeds the borrowed quantity for document ID: " + documentId);
                    }

                    // Update the document record
                    String updateSql = "UPDATE DocumentBorrowed023 SET quantity = quantity - ? WHERE Document023id = ? AND BorrowingOrder023id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, returnQuantity);
                        updateStmt.setInt(2, documentId);
                        updateStmt.setInt(3, borrowingOrderId);
                        updateStmt.executeUpdate();
                    }

                    // Update the stock
                    updateDocumentStock(documentId, -returnQuantity); // Add back to inventory
                    System.out.println("Successfully processed return for document ID " + documentId);
                } else {
                    System.out.println("Document ID " + documentId + " does not exist in this borrowing order.");
                }
            } catch (SQLException e) {
                throw new SQLException("Error while processing the return for document ID " + documentId + ": " + e.getMessage(), e);
            }
        }
    }

    public void updateDocumentStock(int documentId, int quantity) throws SQLException {
        String sql = "UPDATE Document023 SET stock = stock - ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, documentId);
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        try {
            DocumentBorrow023DAO documentBorrowDAO = new DocumentBorrow023DAO();
            Document023DAO documentDAO = new Document023DAO();
            BorrowingOrder023DAO borrowingOrderDAO = new BorrowingOrder023DAO(documentBorrowDAO.connection);
            ReturnOrder023DAO returnOrderDAO = new ReturnOrder023DAO(); // Tạo đối tượng DAO cho phiếu trả
            // Phần test cho phiếu mượn
            testAddDocumentToBorrow(documentBorrowDAO, documentDAO, borrowingOrderDAO);
            // Phần test cho trả 
//            testAddDocumentsToReturnOrder(documentBorrowDAO, returnOrderDAO);
            // Phần test cho lịch sử trả tài liệu
//            testGetReturnHistory(documentBorrowDAO, borrowingOrderDAO);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void testAddDocumentToBorrow(DocumentBorrow023DAO documentBorrowDAO, Document023DAO documentDAO, BorrowingOrder023DAO borrowingOrderDAO) throws SQLException {
        // Kiểm tra phiếu mượn
        BorrowingOrder023 order = borrowingOrderDAO.getBorrowingOrderById(1);
        System.out.println("Đã tạo phiếu mượn thành công với ID: " + order.getId());

        // Lấy tài liệu muốn mượn bằng phương thức getById
        int documentId = 2; // ID tài liệu muốn mượn
        Document023 documentToBorrow = documentDAO.getDocumentById(documentId);

        // Kiểm tra xem tài liệu có tồn tại không
        if (documentToBorrow == null) {
            System.out.println("Tài liệu với ID " + documentId + " không tồn tại.");
            return;
        }

        // Thêm tài liệu đã mượn
        DocumentBorrowed023 newBorrowedDocument = new DocumentBorrowed023();
        newBorrowedDocument.setDocument(documentToBorrow);
        newBorrowedDocument.setQuantity(5); // Số lượng mượn
        newBorrowedDocument.setBorrowingOrder(order); // Gán đơn mượn cho tài liệu
        // Thêm tài liệu đã mượn vào phiếu mượn
        documentBorrowDAO.addDocumentBorrowed(newBorrowedDocument);
        System.out.println("Đã thêm tài liệu mượn thành công!");
    }
}
