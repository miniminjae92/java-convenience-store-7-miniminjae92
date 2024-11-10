package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PromotionProductTest {
    @Test
    public void testPromotionProductInitialization() {
        PromotionProduct promotionProduct = new PromotionProduct("콜라", 1000, 10, "탄산2+1");
        assertEquals(10, promotionProduct.getPromotionStock(), "프로모션 재고가 올바르게 초기화되지 않았습니다.");
        assertEquals(10, promotionProduct.getStock(), "일반 재고가 올바르게 초기화되지 않았습니다.");
    }
}
