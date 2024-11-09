package store.domain;

import store.dto.ProductDTO;

public class PromotionProduct extends Product {
    private int stock;
    private final String promotionType;

    public PromotionProduct(String name, int price, int stock, String promotionType) {
        super(name, price, stock);
        this.promotionType = promotionType;
    }

    public void reducePromotionStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("[ERROR] 프로모션 재고가 부족합니다.");
        }
        stock -= quantity;
    }

    @Override
    public ProductDTO toDTO() {
        return new ProductDTO(getName(), getPrice(), getStock(), promotionType);
    }
}
