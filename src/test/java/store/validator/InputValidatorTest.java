package store.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import store.domain.Product;

import java.util.Map;

class InputValidatorTest {

    @Test
    void testInvalidFormat() {
        String input = "[잘못된형식]";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateFormat(input);
        });
        assertEquals("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.", exception.getMessage());
    }

    @Test
    void testProductExists() {
        Map<String, Product> availableProducts = Map.of("사이다", new Product("사이다", 1000, 10));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateProductExists(availableProducts, "콜라");
        });
        assertEquals("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.", exception.getMessage());
    }

    @Test
    void testStockQuantity() {
        Product product = new Product("사이다", 1000, 5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateStockQuantity(product, 10);
        });
        assertEquals("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", exception.getMessage());
    }
}
