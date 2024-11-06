package Model;

public class DocumentBorrowed023 {

    private int id;
    private int quantity;
    private Document023 document; // Thêm đối tượng Document023
    private BorrowingOrder023 borrowingOrder; // Thêm đối tượng BorrowingOrder023
    private ReturnOrder023 returnOrder; // Thêm đối tượng ReturnOrder023

    // Constructors
    public DocumentBorrowed023() {
    }

    public DocumentBorrowed023(int id, int quantity, Document023 document, BorrowingOrder023 borrowingOrder, ReturnOrder023 returnOrder) {
        this.id = id;
        this.quantity = quantity;
        this.document = document;
        this.borrowingOrder = borrowingOrder;
        this.returnOrder = returnOrder;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Document023 getDocument() {
        return document;
    }

    public void setDocument(Document023 document) {
        this.document = document;
    }

    public BorrowingOrder023 getBorrowingOrder() {
        return borrowingOrder;
    }

    public void setBorrowingOrder(BorrowingOrder023 borrowingOrder) {
        this.borrowingOrder = borrowingOrder;
    }

    public ReturnOrder023 getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(ReturnOrder023 returnOrder) {
        this.returnOrder = returnOrder;
    }
}
