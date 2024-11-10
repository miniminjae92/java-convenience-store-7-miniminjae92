package store.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.dto.ProductDTO;

class ProductRepositoryTest {
    private ProductRepository productRepository;
    private PromotionProduct colaPromotion;
    private Product cider;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        colaPromotion = new PromotionProduct("콜라", 1000, 10, "탄산2+1");
        cider = new Product("사이다", 1000, 8);
    }

    @Test
    @DisplayName("일반 상품 및 프로모션 상품 저장 및 조회 테스트")
    void saveAndFindProduct() {
        productRepository.save(cider);
        productRepository.savePromotion(colaPromotion);

        Product retrievedCider = productRepository.findByName("사이다");
        PromotionProduct retrievedColaPromotion = (PromotionProduct) productRepository.findPromotionByName("콜라");

        assertThat(retrievedCider).isEqualTo(cider);
        assertThat(retrievedColaPromotion).isEqualTo(colaPromotion);
    }

    @Test
    @DisplayName("존재하지 않는 일반 상품 조회 시 예외 발생")
    void findNonExistentProductThrowsException() {
        assertThatThrownBy(() -> productRepository.findByName("에너지바"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 상품입니다: 에너지바");
    }

    @Test
    @DisplayName("존재하지 않는 프로모션 상품 조회 시 예외 발생")
    void findNonExistentPromotionProductThrowsException() {
        assertThatThrownBy(() -> productRepository.findPromotionByName("사이다"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 프로모션 상품입니다: 사이다");
    }

    @Test
    @DisplayName("상품 전체 조회 테스트")
    void findAllProducts() {
        productRepository.save(cider);
        productRepository.savePromotion(colaPromotion);

        List<ProductDTO> allProducts = productRepository.findAllAsDTO();
        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).anyMatch(product -> product.getName().equals("콜라"));
        assertThat(allProducts).anyMatch(product -> product.getName().equals("사이다"));
    }

    @Test
    @DisplayName("프로모션 상품 업데이트 테스트")
    void updatePromotionProduct() {
        productRepository.savePromotion(colaPromotion);

        PromotionProduct updatedColaPromotion = new PromotionProduct("콜라", 1200, 10, "탄산2+1");
        productRepository.updatePromotionProduct("콜라", updatedColaPromotion);

        PromotionProduct retrievedColaPromotion = (PromotionProduct) productRepository.findPromotionByName("콜라");
        assertThat(retrievedColaPromotion.getPrice()).isEqualTo(1200);
        assertThat(retrievedColaPromotion.getPromotionType()).isEqualTo("탄산2+1");
    }

    @Test
    @DisplayName("존재하지 않는 일반 상품 업데이트 시 예외 발생")
    void updateNonExistentProductThrowsException() {
        assertThatThrownBy(() -> productRepository.updateProduct("에너지바", cider))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        productRepository.save(cider);
        productRepository.delete("사이다", "regular");

        assertThatThrownBy(() -> productRepository.findByName("사이다"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 상품입니다: 사이다");
    }

    @Test
    @DisplayName("모든 상품 삭제 테스트")
    void deleteAllProducts() {
        productRepository.save(cider);
        productRepository.savePromotion(colaPromotion);

        productRepository.deleteAll();
        assertThat(productRepository.findAllAsDTO()).isEmpty();
    }
}
