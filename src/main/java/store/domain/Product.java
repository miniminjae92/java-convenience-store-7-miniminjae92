package store.domain;

import store.common.ErrorMessage;
import store.dto.ProductDTO;

public class Product {
    private final String name;
    private final int price;
    private int stock;
    private final String promotionType;

    public Product(String name, int price, int stock, String promotionType) {
        validateName(name);
        validatePrice(price);
        validateStock(stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotionType = promotionType;
    }

    public Product(String name, int price, int stock) {
        this(name, price, stock, null);
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

    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalStateException(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
        this.stock -= quantity;
    }

    public ProductDTO toDTO() {
        return new ProductDTO(
                this.name,
                this.price,
                this.stock,
                this.promotionType
        );
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalStateException(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }
}
