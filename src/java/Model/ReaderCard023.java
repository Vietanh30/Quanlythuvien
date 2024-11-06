package Model;

import java.util.Date;

public class ReaderCard023 {

    private int id;
    private String code;
    private Date issueDate; // Thay đổi từ String sang Date
    private Date expirationDate; // Thay đổi từ String sang Date
    private int readerId;

    public ReaderCard023() {
    }

    // Constructors, getters, and setters
    public ReaderCard023(int id, String code, Date issueDate, Date expirationDate, int readerId) {
        this.id = id;
        this.code = code;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.readerId = readerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getIssueDate() {
        return issueDate; // Trả về Date
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate; // Nhận vào Date
    }

    public Date getExpirationDate() {
        return expirationDate; // Trả về Date
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate; // Nhận vào Date
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}