package Model;

public class Librarian023 {

    private int memberId;
    private String position;
    private Member023 member; // Thêm thuộc tính để lưu thông tin thành viên

    // Constructors
    public Librarian023() {
    }

    // Constructor với thông tin thành viên
    public Librarian023(int memberId, String position, Member023 member) {
        this.memberId = memberId;
        this.position = position;
        this.member = member; // Khởi tạo thông tin thành viên
    }

    // Getters and setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Member023 getMember() {
        return member;
    }

    public void setMember(Member023 member) {
        this.member = member;
    }
}