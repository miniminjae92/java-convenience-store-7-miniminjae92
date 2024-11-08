package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @Test
    @DisplayName("유효한 상품 생성")
    void createValidProduct() {
        Product product = new Product("콜라", 1000);
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("빈 이름으로 상품 생성 시 예외 발생")
    void createProductWithEmptyNameThrowsException() {
        assertThatThrownBy(() -> new Product("", 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }

    @Test
    @DisplayName("null 이름으로 상품 생성 시 예외 발생")
    void createProductWithNullNameThrowsException() {
        assertThatThrownBy(() -> new Product(null, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }

    @Test
    @DisplayName("0 가격으로 상품 생성 시 예외 발생")
    void createProductWithZeroPriceThrowsException() {
        assertThatThrownBy(() -> new Product("콜라", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품 가격은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("음수 가격으로 상품 생성 시 예외 발생")
    void createProductWithNegativePriceThrowsException() {
        assertThatThrownBy(() -> new Product("콜라", -500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품 가격은 0보다 커야 합니다.");
    }
}
