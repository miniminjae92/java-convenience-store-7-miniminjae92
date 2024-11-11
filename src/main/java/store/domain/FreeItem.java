package store.domain;

public class FreeItem {
    private final String productName;
    private final int freeQuantity;

    public FreeItem(String productName, int freeQuantity) {
        this.productName = productName;
        this.freeQuantity = freeQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }
}
