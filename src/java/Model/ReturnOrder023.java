package Model;

public class ReturnOrder023 {

    private int id;
    private String returnDate;
    private int fine;
    private int readerId;
    private int librarianId;
    private Reader023 reader; // Thêm thuộc tính để lưu thông tin độc giả

    public ReturnOrder023() {
    }

    // Constructor với thông tin độc giả và thành viên
    public ReturnOrder023(int id, String returnDate, int fine, int readerId, int librarianId, Reader023 reader) {
        this.id = id;
        this.returnDate = returnDate;
        this.fine = fine;
        this.readerId = readerId;
        this.librarianId = librarianId;
        this.reader = reader;
    }

    // Getters and setters
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

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
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

    public Reader023 getReader() {
        return reader;
    }

    public void setReader(Reader023 reader) {
        this.reader = reader;
    }
}
