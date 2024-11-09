package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.config.DataLoader;
import store.domain.Cart;
import store.domain.Product;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutServiceTest {
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;
    private CheckoutService checkoutService;
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        checkoutService = new CheckoutService(productRepository, promotionRepository);
        dataLoader = new DataLoader(productRepository, promotionRepository);

        dataLoader.initializeData("src/main/resources/products.md", "src/main/resources/promotions.md");
    }

    @Test
    @DisplayName("총 금액 계산 테스트")
    void calculateTotalAmount() {
        Cart cart = new Cart();
        Product cola = new Product("콜라", 1000, 10, "null");
        Product cider = new Product("사이다", 1200, 8, "null");
        cart.addItem(cola, 2);
        cart.addItem(cider, 3);

        int totalAmount = checkoutService.calculateTotalAmount(cart);

        assertThat(totalAmount).isEqualTo(1000 * 2 + 1200 * 3); // 콜라와 사이다의 가격 기준
    }
}

