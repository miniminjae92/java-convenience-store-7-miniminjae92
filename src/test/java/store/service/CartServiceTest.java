package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Cart;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.service.CartService;
import store.repository.ProductRepository;

import java.util.Map;

public class CartServiceTest {

    private CartService cartService;
    private Cart cart;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        productRepository = new ProductRepository();
        cartService = new CartService(cart, productRepository);

        // 테스트용 제품 미리 저장
        productRepository.save(new Product("콜라", 1000, 10));
        productRepository.savePromotion(new PromotionProduct("콜라", 1000, 5, "탄산2+1"));
    }

    @Test
    void getCartItems_shouldCombineQuantitiesForSameProductName() {
        // Given
        Product regularProduct = new Product("콜라", 1000, 10);
        PromotionProduct promotionProduct = new PromotionProduct("콜라", 1000, 5, "탄산2+1");

        cartService.addItemToCart(regularProduct.getName(), 3);
        cartService.addItemToCart(promotionProduct.getName(), 2);

        // When
        Map<String, Integer> cartItems = cartService.getCartItems();

        // Then
        assertThat(cartItems).containsOnlyKeys("콜라");
        assertThat(cartItems.get("콜라")).isEqualTo(5); // 3 (일반 상품) + 2 (프로모션 상품)
    }

    @Test
    void getCartItems_shouldReturnZeroForNonexistentProduct() {
        // When
        Map<String, Integer> cartItems = cartService.getCartItems();

        // Then
        assertThat(cartItems).doesNotContainKey("없는상품");
        assertThat(cartItems.getOrDefault("없는상품", 0)).isEqualTo(0);
    }

    @Test
    void getCartItems_shouldHandleMultipleProducts() {
        // Given
        Product product1 = new Product("콜라", 1000, 10);
        Product product2 = new Product("사이다", 1000, 10);

        cartService.addItemToCart(product1.getName(), 5);
        cartService.addItemToCart(product2.getName(), 3);

        // When
        Map<String, Integer> cartItems = cartService.getCartItems();

        // Then
        assertThat(cartItems).containsOnlyKeys("콜라", "사이다");
        assertThat(cartItems.get("콜라")).isEqualTo(5);
        assertThat(cartItems.get("사이다")).isEqualTo(3);
    }
}

