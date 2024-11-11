package store.config;

import java.util.HashSet;
import java.util.Set;
import store.common.StringUtils;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public DataLoader(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initializeData(String productFilePath, String promotionFilePath) {
        // 제품과 프로모션을 각각 로드하여 저장
        List<Product> products = loadProducts(productFilePath);
        List<Promotion> promotions = loadPromotions(promotionFilePath);

        // 원본 제품 리스트에 저장
        productRepository.saveAll(products);
        // 프로모션 리스트 저장
        promotions.forEach(promotionRepository::save);

        // 분류된 인벤토리로 저장
        productRepository.populateInventories();
    }

    private List<Product> loadProducts(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().skip(1).forEach(line -> {
                String[] data = StringUtils.parseData(line, 4);
                String name = data[0];
                int price = Integer.parseInt(data[1]);
                int quantity = Integer.parseInt(data[2]);
                String promotionType = data[3].equals("null") ? null : data[3];

                if (promotionType != null) {
                    // 프로모션 제품 추가
                    Product promoProduct = new Product(name, price, quantity, promotionType);
                    products.add(promoProduct);

                    // 일반 제품도 초기화해서 추가 (수량 0으로 설정)
                    Product regularProduct = new Product(name, price, 0, null);
                    products.add(regularProduct);
                } else {
                    // 일반 제품만 추가
                    Product regularProduct = new Product(name, price, quantity, null);
                    products.add(regularProduct);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("파일 읽기 에러", e);
        }
        return products;
    }

    private List<Promotion> loadPromotions(String filePath) {
        List<Promotion> promotions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().skip(1).forEach(line -> {
                String[] data = StringUtils.parseData(line, 5);
                String promotionType = data[0];
                int buyQuantity = Integer.parseInt(data[1]);
                int freeQuantity = Integer.parseInt(data[2]);
                LocalDateTime startDate = LocalDateTime.parse(data[3] + "T00:00:00");
                LocalDateTime endDate = LocalDateTime.parse(data[4] + "T23:59:59");

                Promotion promotion = new Promotion(promotionType, buyQuantity, freeQuantity, startDate, endDate);
                promotions.add(promotion);
            });
        } catch (IOException e) {
            throw new IllegalStateException("파일 읽기 에러", e);
        }
        return promotions;
    }
}
