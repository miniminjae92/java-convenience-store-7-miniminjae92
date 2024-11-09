package store.dto;

public class ProductDTO {
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

    public int getStock() {
        return stock;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public String getStockInfo() {
        if (stock > 0) {
            return stock + "개";
        }
        return "재고 없음";
    }

    public String getPromotionInfo() {
        if (promotionType != null) {
            return promotionType;
        }
        return "";
    }
}
