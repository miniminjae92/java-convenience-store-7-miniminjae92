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
}
