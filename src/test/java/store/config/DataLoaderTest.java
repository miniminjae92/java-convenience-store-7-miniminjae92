package store.config;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.io.File;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DataLoaderTest {

    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        promotionRepository = new PromotionRepository();
        dataLoader = new DataLoader(productRepository, promotionRepository);
    }

    @Test
    @DisplayName("일부 상품과 프로모션이 정상적으로 로드되는지 확인한다.")
    void initializeData_checkSampleProductsAndPromotions() {
        File productFile = new File("src/main/resources/products.md");
        File promotionFile = new File("src/main/resources/promotions.md");

        dataLoader.initializeData(productFile.getPath(), promotionFile.getPath());

        Product cola = productRepository.findByName("콜라");
        assertThat(cola).isNotNull();
        assertThat(cola.getPrice()).isEqualTo(1000);

        Product water = productRepository.findByName("물");
        assertThat(water).isNotNull();
        assertThat(water.getPrice()).isEqualTo(500);

        Promotion promotion = promotionRepository.findByName("탄산2+1");
        assertThat(promotion).isNotNull();
        assertThat(promotion.getBuyQuantity()).isEqualTo(2);
        assertThat(promotion.getStartDate()).isEqualTo(LocalDateTime.parse("2024-01-01" + "T00:00:00"));
    }
}
