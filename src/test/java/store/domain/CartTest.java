package store.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTest {

    @Test
    void 카트에_상품을_추가하고_수량을_반환한다() {
        Product product = new Product("콜라", 1000);
        Cart cart = new Cart();
        cart.addItem(product, 3);

        assertThat(cart.getQuantity(product)).isEqualTo(3);
    }
}
