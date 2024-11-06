package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Library023; 
import Model.School023; 
import java.util.ArrayList;
import java.util.List;

public class Library023DAO {

    private Connection connection;

    public Library023DAO() throws SQLException, ClassNotFoundException {
        connection = ConnectDatabase.getMySQLConnection();
    }

    public List<Library023> getAllLibraries() throws SQLException {
        List<Library023> libraries = new ArrayList<>();
        String sql = "SELECT l.*, s.name AS school_name, s.address AS school_address, s.description AS school_description "
                + "FROM Library023 l LEFT JOIN School023 s ON l.school023id = s.id";

        try (PreparedStatement pstm = connection.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                Library023 library = extractLibraryFromResultSet(rs);
                libraries.add(library);
            }
        }
        return libraries;
    }

    public Library023 getLibraryById(int id) throws SQLException {
        Library023 library = null;
        String sql = "SELECT l.*, s.name AS school_name, s.address AS school_address, s.description AS school_description "
                + "FROM Library023 l LEFT JOIN School023 s ON l.school023id = s.id WHERE l.id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    library = extractLibraryFromResultSet(rs);
                }
            }
        }
        return library;
    }

    private School023 extractSchoolFromResultSet(ResultSet rs) throws SQLException {
        int schoolId = rs.getInt("school023id");
        String schoolName = rs.getString("school_name");
        String schoolAddress = rs.getString("school_address");
        String schoolDescription = rs.getString("school_description");
        return new School023(schoolId, schoolName, schoolAddress, schoolDescription);
    }

    private Library023 extractLibraryFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String description = rs.getString("description");
        int schoolId = rs.getInt("school023id"); // Đảm bảo tên cột khớp

        return new Library023(id, name, address, description, schoolId);
    }

    public static void main(String[] args) {
        try {
            Library023DAO libraryDAO = new Library023DAO();
            System.out.println("Tất cả các thư viện:");
            List<Library023> libraries = libraryDAO.getAllLibraries();
            for (Library023 library : libraries) {
                printLibraryInfo(library);
            }

            int testId = 1; 
            Library023 libraryById = libraryDAO.getLibraryById(testId);
            if (libraryById != null) {
                System.out.println("Thông tin thư viện với ID " + testId + ":");
                printLibraryInfo(libraryById);
            } else {
                System.out.println("Không tìm thấy thư viện với ID " + testId);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class Not Found Exception: " + e.getMessage());
        }
    }

    private static void printLibraryInfo(Library023 library) {
        System.out.println("ID: " + library.getId());
        System.out.println("Name: " + library.getName());
        System.out.println("Address: " + library.getAddress());
        System.out.println("Description: " + library.getDescription());
        System.out.println("School ID: " + library.getSchoolId());
        System.out.println("----------");
    }
}