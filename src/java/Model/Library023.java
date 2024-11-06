package Model;

public class Library023 {

    private int id;
    private String name;
    private String address;
    private String description;
    private int schoolId;

    // Constructors, getters, and setters
    public Library023(int id, String name, String address, String description, int schoolId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.schoolId = schoolId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
