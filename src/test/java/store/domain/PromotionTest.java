package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromotionTest {
    @Test
    @DisplayName("유효한 프로모션 생성")
    void createValidPromotion() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(5);
        Promotion promotion = new Promotion(2, 1, start, end);

        assertThat(promotion.getBuyQuantity()).isEqualTo(2);
        assertThat(promotion.getFreeQuantity()).isEqualTo(1);
        assertThat(promotion.isActive(LocalDateTime.now())).isTrue();
    }

    @ParameterizedTest(name = "buyQuantity={0}, freeQuantity={1}")
    @CsvSource({
            "0,1",
            "-1,2",
            "2,0",
            "3,-2"
    })
    @DisplayName("잘못된 수량으로 프로모션 생성 시 예외 발생")
    void createPromotionWithInvalidQuantities(int buyQty, int freeQty) {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(5);

        assertThatThrownBy(() -> new Promotion(buyQty, freeQty, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 프로모션 수량은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("프로모션 활성 여부 테스트 - 활성 기간 내")
    void isPromotionActiveWithinPeriod() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        Promotion promotion = new Promotion(2, 1, start, end);

        assertThat(promotion.isActive(LocalDateTime.now())).isTrue();
    }

    @Test
    @DisplayName("프로모션 활성 여부 테스트 - 활성 기간 전")
    void isPromotionActiveBeforeStart() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(5);
        Promotion promotion = new Promotion(2, 1, start, end);

        assertThat(promotion.isActive(LocalDateTime.now())).isFalse();
    }

    @Test
    @DisplayName("프로모션 활성 여부 테스트 - 활성 기간 후")
    void isPromotionActiveAfterEnd() {
        LocalDateTime start = LocalDateTime.now().minusDays(5);
        LocalDateTime end = LocalDateTime.now().minusDays(1);
        Promotion promotion = new Promotion(2, 1, start, end);

        assertThat(promotion.isActive(LocalDateTime.now())).isFalse();
    }

    @ParameterizedTest(name = "quantity={0}, expectedPayable={1}, expectedFree={2}")
    @CsvSource({
            "1,1,0",
            "2,2,0",
            "3,2,1",
            "4,3,1",
            "5,4,1",
            "6,4,2",
            "7,5,2",
            "8,6,2",
            "9,6,3"
    })
    @DisplayName("프로모션 적용 시 지불 수량 및 무료 수량 계산 테스트")
    void calculatePromotion(int quantity, int expectedPayable, int expectedFree) {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(5);
        Promotion promotion = new Promotion(2, 1, start, end);

        int[] result = promotion.calculatePromotion(quantity);

        assertThat(result[0]).isEqualTo(expectedPayable);
        assertThat(result[1]).isEqualTo(expectedFree);
    }
}
