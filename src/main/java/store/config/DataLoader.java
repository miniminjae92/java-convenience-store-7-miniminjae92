package store.config;

import store.common.ErrorMessage;
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
        List<Product> products = loadProducts(productFilePath);
        List<Promotion> promotions = loadPromotions(promotionFilePath);

        productRepository.saveAll(products);
        promotions.forEach(promotionRepository::save);

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

                String promotionType = parsePromotionType(data[3]);

                addProduct(products, name, price, quantity, promotionType);
            });
        } catch (IOException e) {
            throw new IllegalStateException(ErrorMessage.FILE_READ_ERROR.getMessage(), e);
        }
        return products;
    }

    private String parsePromotionType(String promotionData) {
        if ("null".equals(promotionData)) {
            return null;
        }
        return promotionData;
    }

    private void addProduct(List<Product> products, String name, int price, int quantity, String promotionType) {
        if (promotionType != null) {
            Product promoProduct = new Product(name, price, quantity, promotionType);
            products.add(promoProduct);

            Product regularProduct = new Product(name, price, 0, null);
            products.add(regularProduct);
            return;
        }
        Product regularProduct = new Product(name, price, quantity, null);
        products.add(regularProduct);
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
            throw new IllegalStateException(ErrorMessage.FILE_READ_ERROR.getMessage(), e);
        }
        return promotions;
    }
}
