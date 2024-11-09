package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTest {

    @Test
    @DisplayName("유효한 상품 생성")
    void createValidProduct() {
        PromotionProduct product = new PromotionProduct("콜라", 1000, 10, "탄산2+1");

        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getStock()).isEqualTo(10);
        assertThat(product.getPromotionType()).isEqualTo("탄산2+1");
    }

    @Test
    @DisplayName("빈 이름으로 상품 생성 시 예외 발생")
    void createProductWithEmptyNameThrowsException() {
        assertThatThrownBy(() -> new PromotionProduct("", 1000, 10, "탄산2+1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }

    @Test
    @DisplayName("null 이름으로 상품 생성 시 예외 발생")
    void createProductWithNullNameThrowsException() {
        assertThatThrownBy(() -> new PromotionProduct(null, 1000, 10, "탄산2+1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }

    @Test
    @DisplayName("0 가격으로 상품 생성 시 예외 발생")
    void createProductWithZeroPriceThrowsException() {
        assertThatThrownBy(() -> new PromotionProduct("콜라", 0, 10, "탄산2+1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 가격으로 상품 생성 시 예외 발생")
    void createProductWithNegativePriceThrowsException() {
        assertThatThrownBy(() -> new PromotionProduct("콜라", -500, 10, "탄산2+1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("프로모션이 없는 상품 생성 시 promotionName은 null이어야 한다")
    void createProductWithoutPromotion() {
        PromotionProduct product = new PromotionProduct("사이다", 1000, 8, "null");

        assertThat(product.getPromotionType()).isEqualTo("null");
    }

    @Test
    @DisplayName("프로모션이 없는 상품 생성 시 재고 확인")
    void createProductWithoutPromotionAndCheckStock() {
        Product product = new Product("사이다", 1000, 8);

        assertThat(product.getStock()).isEqualTo(8);
    }
}
