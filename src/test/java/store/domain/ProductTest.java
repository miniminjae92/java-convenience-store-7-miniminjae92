package store.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    void 상품_객체가_생성되고_가격을_반환한다() {
        String name = "콜라";
        int price = 1000;
        Product product = new Product(name, price);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }
}
