
package store.domain;

import java.util.Objects;
import store.dto.ProductDTO;

public class PromotionProduct extends Product {
    private int promotionStock;
    private final String promotionType;

    public PromotionProduct(String name, int price, int stock, String promotionType) {
        super(name, price, stock);
        this.promotionType = promotionType;
        this.promotionStock = stock;  // 기본적으로 초기 프로모션 재고를 일반 재고와 동일하게 설정
    }

    public String getPromotionType() {
        return promotionType;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public int getTotalStock() {
        return getStock() + promotionStock;
    }

    public void reducePromotionStock(int quantity) {
        int promoStockToUse = Math.min(promotionStock, quantity); // 사용할 프로모션 재고
        promotionStock -= promoStockToUse;
        int remainingQty = quantity - promoStockToUse; // 일반 재고에서 차감할 남은 수량

        if (remainingQty > 0) {
            reduceStock(remainingQty); // 남은 수량을 일반 재고에서 차감
        }
    }

    @Override
    public ProductDTO toDTO() {
        return new ProductDTO(getName(), getPrice(), getStock(), promotionType);  // 필요시 promotionStock 포함
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PromotionProduct)) return false;
        if (!super.equals(o)) return false;
        PromotionProduct that = (PromotionProduct) o;
        return promotionType.equals(that.promotionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), promotionType);
    }
}