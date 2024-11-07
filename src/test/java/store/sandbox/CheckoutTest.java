package store.sandbox;

import org.junit.jupiter.api.Test;
import store.domain.Cart;
import store.domain.Checkout;
import store.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;

// 체크아웃 - 장바구니 총 구매액(상품별 가격 * 수량), 프로모션 할인 적용, 멤버쉽 할인 정책 반영 = 최종 결제 금액
public class CheckoutTest {

    @Test
    void 체크아웃에서_장바구니의_총_구매액을_계산한다() {
        Product product1 = new Product("콜라", 1000);
        Product product2 = new Product("사이다", 1500);

        Cart cart = new Cart();
        cart.addItem(product1, 3);
        cart.addItem(product2, 2);

        Checkout checkout = new Checkout();
        int total = checkout.calculateTotalPurchaseAmount(cart);

        assertThat(total).isEqualTo(6000);
    }


}
