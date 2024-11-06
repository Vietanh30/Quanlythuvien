/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class DocumentPurchase023 {

    private int id;
    private int quantity;
    private int purchasePrice;
    private int documentId;
    private int purchaseOrderId;

    // Constructors, getters, and setters
    public DocumentPurchase023(int id, int quantity, int purchasePrice, int documentId, int purchaseOrderId) {
        this.id = id;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.documentId = documentId;
        this.purchaseOrderId = purchaseOrderId;
    }

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

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
}
