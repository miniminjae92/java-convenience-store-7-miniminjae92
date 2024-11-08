package store.repository;

import store.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductRepositoryTest {
    private ProductRepository productRepository;
    private Product cola;
    private Product cider;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        cola = new Product("콜라", 1000);
        cider = new Product("사이다", 1000);
    }

    @Test
    @DisplayName("상품 추가 및 조회 테스트 - 일반 재고")
    void addAndRetrieveProductGeneralStock() {
        productRepository.addProduct(cola, 10, false);
        Product retrieved = productRepository.getProduct("콜라");
        assertThat(retrieved).isEqualTo(cola);
        assertThat(productRepository.getGeneralStock("콜라")).isEqualTo(10);
        assertThat(productRepository.getPromotionStock("콜라")).isEqualTo(0);
    }

    @Test
    @DisplayName("상품 추가 및 조회 테스트 - 프로모션 재고")
    void addAndRetrieveProductPromotionStock() {
        productRepository.addProduct(cider, 5, true);
        Product retrieved = productRepository.getProduct("사이다");
        assertThat(retrieved).isEqualTo(cider);
        assertThat(productRepository.getGeneralStock("사이다")).isEqualTo(0);
        assertThat(productRepository.getPromotionStock("사이다")).isEqualTo(5);
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    void getNonExistentProductThrowsException() {
        assertThatThrownBy(() -> productRepository.getProduct("에너지바"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 상품입니다.");
    }

    @Test
    @DisplayName("상품 재고 감소 테스트 - 일반 재고")
    void reduceProductStockGeneral() {
        productRepository.addProduct(cola, 10, false);
        productRepository.reduceStock("콜라", 2, false);
        assertThat(productRepository.getGeneralStock("콜라")).isEqualTo(8);
    }

    @Test
    @DisplayName("상품 재고 감소 테스트 - 프로모션 재고")
    void reduceProductStockPromotion() {
        productRepository.addProduct(cider, 5, true);
        productRepository.reduceStock("사이다", 3, true);
        assertThat(productRepository.getPromotionStock("사이다")).isEqualTo(2);
    }

    @Test
    @DisplayName("재고 부족 시 일반 재고 감소 시 예외 발생")
    void reduceGeneralStockInsufficient() {
        productRepository.addProduct(cola, 3, false);
        assertThatThrownBy(() -> productRepository.reduceStock("콜라", 5, false))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 일반 재고가 부족합니다.");
    }

    @Test
    @DisplayName("재고 부족 시 프로모션 재고 감소 시 예외 발생")
    void reducePromotionStockInsufficient() {
        productRepository.addProduct(cider, 3, true);
        assertThatThrownBy(() -> productRepository.reduceStock("사이다", 5, true))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 프로모션 재고가 부족합니다.");
    }

    @Test
    @DisplayName("다중 상품 추가 및 재고 조회 테스트")
    void addMultipleProductsAndCheckStocks() {
        productRepository.addProduct(cola, 10, false);
        productRepository.addProduct(cider, 5, true);
        productRepository.addProduct(cola, 5, true);

        assertThat(productRepository.getGeneralStock("콜라")).isEqualTo(10);
        assertThat(productRepository.getPromotionStock("콜라")).isEqualTo(5);
        assertThat(productRepository.getPromotionStock("사이다")).isEqualTo(5);
    }
}
