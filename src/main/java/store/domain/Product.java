package store.domain;

import store.dto.ProductDTO;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final String promotionType;

    public Product(String name, int price, int stock, String promotionType) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotionType = promotionType;
    }

    // 프로모션이 없는 상품을 위한 생성자
    public Product(String name, int price, int stock) {
        this(name, price, stock, null);
    }

    // Getter 메서드
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public String getPromotionType() { return promotionType; }

    // 재고 차감 메서드
    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("[ERROR] 일반 재고가 부족합니다.");
        }
        stock -= quantity;
    }

    public ProductDTO toDTO() {
        return new ProductDTO(
                this.name,
                this.price,
                this.stock,
                this.promotionType != null ? this.getPromotionType() : null
        );
    }

    // 검증 메서드
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 필수 입력 사항입니다.");
        }
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 가격은 0보다 커야 합니다.");
        }
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("[ERROR] 재고는 음수일 수 없습니다.");
        }
    }
}
