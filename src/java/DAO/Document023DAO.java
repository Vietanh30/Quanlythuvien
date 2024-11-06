package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Document023; // Đảm bảo bạn có lớp Document023

public class Document023DAO {

    private Connection connection;

    public Document023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection();
    }

    public List<Document023> getAllDocument() throws SQLException {
        List<Document023> documents = new ArrayList<>();
        String sql = "SELECT d.*, l.id AS library_id "
                + "FROM Document023 d LEFT JOIN Library023 l ON d.library023id = l.id"; // Truy vấn lấy tất cả tài liệu cùng thông tin thư viện

        try (PreparedStatement pstm = connection.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                // Lấy dữ liệu của tài liệu
                int id = rs.getInt("d.id");
                String name = rs.getString("d.name");
                String image = rs.getString("d.image");
                String author = rs.getString("d.author");
                String publicationDate = rs.getString("d.publication_date");
                int quantity = rs.getInt("d.quantity");
                int stock = rs.getInt("d.stock");
                String description = rs.getString("d.description");
                int libraryId = rs.getInt("library_id"); // Lấy ID của thư viện

                Document023 document = new Document023(id, name, image, author, publicationDate, quantity, stock, description, libraryId);
                documents.add(document);
            }
        }
        return documents; // Trả về danh sách tài liệu
    }

    public Document023 getDocumentById(int id) throws SQLException {
        Document023 document = null;
        String sql = "SELECT d.*, l.id AS library_id "
                + "FROM Document023 d LEFT JOIN Library023 l ON d.library023id = l.id WHERE d.id = ?"; // Truy vấn lấy tài liệu theo ID

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id); // Gán giá trị ID vào truy vấn
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Lấy dữ liệu của tài liệu
                    int libraryId = rs.getInt("library_id"); // Lấy ID thư viện

                    String name = rs.getString("d.name");
                    String image = rs.getString("d.image");
                    String author = rs.getString("d.author");
                    String publicationDate = rs.getString("d.publication_date");
                    int quantity = rs.getInt("d.quantity");
                    int stock = rs.getInt("d.stock");
                    String description = rs.getString("d.description");

                    document = new Document023(id, name, image, author, publicationDate, quantity, stock, description, libraryId);
                }
            }
        }
        return document; // Trả về tài liệu hoặc null nếu không tìm thấy
    }

    public static void main(String[] args) {
        try {
            Document023DAO documentDAO = new Document023DAO();

            // Kiểm tra phương thức getAllDocument
            System.out.println("Tất cả các tài liệu:");
            List<Document023> documents = documentDAO.getAllDocument();
            for (Document023 document : documents) {
                printDocumentInfo(document);
            }

            // Kiểm tra phương thức getDocumentById
            int testId = 1; // Thay đổi ID này để kiểm tra tài liệu cụ thể
            Document023 documentById = documentDAO.getDocumentById(testId);
            if (documentById != null) {
                System.out.println("Thông tin tài liệu với ID " + testId + ":");
                printDocumentInfo(documentById);
            } else {
                System.out.println("Không tìm thấy tài liệu với ID " + testId);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class Not Found Exception: " + e.getMessage());
        }
    }

    // Phương thức in thông tin tài liệu
    private static void printDocumentInfo(Document023 document) {
        System.out.println("ID: " + document.getId());
        System.out.println("Name: " + document.getName());
        System.out.println("Image: " + document.getImage());
        System.out.println("Author: " + document.getAuthor());
        System.out.println("Publication Date: " + document.getPublicationDate());
        System.out.println("Quantity: " + document.getQuantity());
        System.out.println("Stock: " + document.getStock());
        System.out.println("Description: " + document.getDescription());
        System.out.println("Library ID: " + document.getLibraryId()); // In ra ID thư viện
        System.out.println("----------");
    }
}
