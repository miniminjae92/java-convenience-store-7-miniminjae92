package store.domain;

import store.dto.ProductDTO;

public class Product {
    private final String name;
    private final int price;
    private int stock;

    public Product(String name, int price, int stock) {
        validateName(name);
        validatePrice(price);
        validateStock(stock);

        this.name = name;
        this.price = price;
        this.stock = stock;
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

    public void reduceStock(int quantity) {
        if (quantity > stock) throw new IllegalArgumentException("[ERROR] 재고가 부족합니다.");
        stock -= quantity;
    }

    public ProductDTO toDTO() {
        return new ProductDTO(getName(), getPrice(), getStock(), null);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }

    private void validatePrice(int price) {
        if (price <= 0) throw new IllegalArgumentException("[ERROR] 가격은 0보다 커야 합니다.");
    }

    private void validateStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("[ERROR] 재고는 음수가 될 수 없습니다.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        // PromotionProduct일 경우, promotionType까지 포함해 비교
        if (this instanceof PromotionProduct && product instanceof PromotionProduct) {
            return price == product.price &&
                    name.equals(product.name) &&
                    ((PromotionProduct) this).getPromotionType().equals(((PromotionProduct) product).getPromotionType());
        }

        // 일반 Product일 경우, 기본 필드만 비교
        return price == product.price &&
                name.equals(product.name);
    }
}