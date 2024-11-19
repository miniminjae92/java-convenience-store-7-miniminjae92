package store.dto;

public class ProductDTO {
    private static final String NO_STOCK = "재고 없음";
    private static final String UNIT = "개";
    private static final String EMPTY_PROMOTION = "";

    private final String name;
    private final int price;
    private final int stock;
    private final String promotionType;

    public ProductDTO(String name, int price, int stock, String promotionType) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotionType = promotionType;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getStockInfo() {
        if (hasStock()) {
            return stock + UNIT;
        }
        return NO_STOCK;
    }

    private boolean hasStock() {
        return stock > 0;
    }

    public String getPromotionInfo() {
        if (promotionType != null && !promotionType.isBlank()) {
            return promotionType;
        }
        return EMPTY_PROMOTION;
    }
}
