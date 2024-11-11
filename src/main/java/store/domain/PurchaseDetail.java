package store.domain;

public class PurchaseDetail {
    private final String productName;
    private final int quantity;
    private final int amount;

    public PurchaseDetail(String productName, int quantity, int amount) {
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }
}
