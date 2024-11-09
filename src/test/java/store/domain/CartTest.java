package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartTest {
    @Test
    @DisplayName("유효한 상품 추가")
    void addValidItem() {
        Cart cart = new Cart();
        Product cola = new Product("콜라", 1000, 10, null);
        cart.addItem(cola, 2);

        assertThat(cart.getItems()).containsEntry(cola, 2);
    }

    @Test
    @DisplayName("기존 상품에 수량 추가")
    void addItemExistingProduct() {
        Cart cart = new Cart();
        Product cola = new Product("콜라", 1000, 10, null);
        cart.addItem(cola, 2);
        cart.addItem(cola, 3);

        assertThat(cart.getItems()).containsEntry(cola, 5);
    }

    @ParameterizedTest(name = "quantity={0}")
    @ValueSource(ints = {0, -1, -5})
    @DisplayName("잘못된 수량으로 상품 추가 시 예외 발생")
    void addItemWithInvalidQuantity(int quantity) {
        Cart cart = new Cart();
        Product cola = new Product("콜라", 1000, 10, null);

        assertThatThrownBy(() -> cart.addItem(cola, quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 수량은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("null 상품으로 추가 시 예외 발생")
    void addItemWithNullProduct() {
        Cart cart = new Cart();

        assertThatThrownBy(() -> cart.addItem(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }
}
