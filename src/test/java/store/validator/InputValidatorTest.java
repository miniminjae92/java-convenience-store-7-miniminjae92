package store.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.PromotionProduct;

import java.util.Map;
@Disabled
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
    void testStockQuantityForRegularProduct() {
        // 일반 상품의 재고를 초과하는 수량을 넣었을 때
        Product product = new Product("사이다", 1000, 5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateStockQuantity(product, 10);  // 10개를 요청하지만 재고는 5개
        });
        assertEquals("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", exception.getMessage());
    }

    @Test
    void testStockQuantityForPromotionProduct() {
        // 프로모션 상품의 재고를 초과하는 수량을 넣었을 때
        PromotionProduct promoProduct = new PromotionProduct("콜라", 1000, 5, "탄산2+1");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            InputValidator.validateStockQuantity(promoProduct, 9);  // 9개 요청, 프로모션 + 일반 재고 = 8개
        });
        assertEquals("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.", exception.getMessage());
    }

    @Test
    void testStockQuantityForPromotionProductWithEnoughStock() {
        // 프로모션 상품과 일반 상품의 재고를 합쳐서 충분히 구매할 수 있는 경우
        PromotionProduct promoProduct = new PromotionProduct("콜라", 1000, 5, "탄산2+1");
        // 7개 요청: 3개는 프로모션 재고에서, 4개는 일반 재고에서 차감
        InputValidator.validateStockQuantity(promoProduct, 7);  // 예외가 발생하지 않으면 정상
    }
}
