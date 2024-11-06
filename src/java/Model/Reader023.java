package Model;

public class Reader023 {

    private int memberId;
    private String readerCode;
    private String className;
    private Member023 member; // Thêm thuộc tính để lưu thông tin thành viên

    public Reader023() {
    }

    // Constructor với thông tin thành viên
    public Reader023(int memberId, String readerCode, String className, Member023 member) {
        this.memberId = memberId;
        this.readerCode = readerCode;
        this.className = className;
        this.member = member; // Khởi tạo thông tin thành viên
    }

    // Getters and setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getReaderCode() {
        return readerCode;
    }

    public void setReaderCode(String readerCode) {
        this.readerCode = readerCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Member023 getMember() {
        return member;
    }

    public void setMember(Member023 member) {
        this.member = member;
    }
}