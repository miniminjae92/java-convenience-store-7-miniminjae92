package store.validator;

import store.domain.Product;
import java.util.Map;

public class InputValidator {
    private static final String PRODUCT_FORMAT_REGEX = "\\[[가-힣a-zA-Z]+-\\d+](,\\[[가-힣a-zA-Z]+-\\d+])*";


    public static void validateFormat(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }

        if (!input.matches(PRODUCT_FORMAT_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static void validateProductExists(Map<String, Product> products, String productName) {
        if (!products.containsKey(productName)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    public static void validateStockQuantity(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
