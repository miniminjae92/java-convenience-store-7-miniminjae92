package store.repository;

import java.util.Map;
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
        cola = new Product("콜라", 1000, 10, "탄산2+1");
        cider = new Product("사이다", 1000, 8, "null");
    }

    @Test
    @DisplayName("상품 저장 및 조회 테스트")
    void saveAndFindProduct() {
        productRepository.save(cola);

        Product retrieved = productRepository.findByName("콜라");
        assertThat(retrieved).isEqualTo(cola);
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    void findNonExistentProductThrowsException() {
        assertThatThrownBy(() -> productRepository.findByName("에너지바"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 전체 조회 테스트")
    void findAllProducts() {
        productRepository.save(cola);
        productRepository.save(cider);

        Map<String, Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).containsKey("콜라");
        assertThat(allProducts).containsKey("사이다");
    }

    @Test
    @DisplayName("상품 업데이트 테스트")
    void updateProduct() {
        productRepository.save(cola);

        Product updatedCola = new Product("콜라", 1200, 10, "탄산2+1");
        productRepository.update("콜라", updatedCola);

        Product retrieved = productRepository.findByName("콜라");
        assertThat(retrieved).isEqualTo(updatedCola);
    }

    @Test
    @DisplayName("존재하지 않는 상품 업데이트 시 예외 발생")
    void updateNonExistentProductThrowsException() {
        assertThatThrownBy(() -> productRepository.update("에너지바", cider))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 해당 이름의 상품이 존재하지 않습니다: 에너지바");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        assertThatThrownBy(() -> productRepository.delete("콜라"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품이 존재하지 않으면 삭제 시 예외 발생")
    void deleteNonExistentProductThrowsException() {
        assertThatThrownBy(() -> productRepository.delete("에너지바"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 상품 삭제 테스트")
    void deleteAllProducts() {
        productRepository.save(cola);
        productRepository.save(cider);

        productRepository.deleteAll();
        assertThat(productRepository.findAll()).isEmpty();
    }
}
