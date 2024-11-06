/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class BorrowingOrder023 {

    private int id;
    private String returnDate;
    private String borrowDate;
    private int readerId;
    private int librarianId;

    // Constructors, getters, and setters
    public BorrowingOrder023() {
    }

    public BorrowingOrder023(int id, String returnDate, String borrowDate, int readerId, int librarianId) {
        this.id = id;
        this.returnDate = returnDate;
        this.borrowDate = borrowDate;
        this.readerId = readerId;
        this.librarianId = librarianId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }
}
