package store.config;

import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class DataLoader {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public DataLoader(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initializeData(String productFilePath, String promotionFilePath) {
        loadPromotions(promotionFilePath);
        loadProducts(productFilePath);
    }

    private void loadPromotions(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().skip(1).forEach(this::processPromotion);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽는 중 문제가 발생했습니다.", e);
        }
    }

    private void loadProducts(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines().skip(1).forEach(this::processProduct);
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽는 중 문제가 발생했습니다.", e);
        }
    }

    private void processPromotion(String line) {
        String[] data = parseData(line, 5);
        String name = data[0];
        int buy = Integer.parseInt(data[1]);
        int get = Integer.parseInt(data[2]);
        LocalDate startDate = LocalDate.parse(data[3]);
        LocalDate endDate = LocalDate.parse(data[4]);

        Promotion promotion = new Promotion(name, buy, get, startDate, endDate);
        promotionRepository.save(promotion);
    }

    private void processProduct(String line) {
        String[] data = parseData(line, 4);
        String name = data[0];
        int price = Integer.parseInt(data[1]);
        int quantity = Integer.parseInt(data[2]);

        Product product = createProduct(name, price, quantity, data[3]);
        saveProduct(product);
    }

    private Product createProduct(String name, int price, int quantity, String promotionType) {
        if ("null".equals(promotionType)) {
            return new Product(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotionType);
    }

    private void saveProduct(Product product) {
        if (isPromotionProduct(product)) {
            productRepository.savePromotion((PromotionProduct) product);
            return;
        }
        productRepository.save(product);
    }

    private boolean isPromotionProduct(Product product) {
        return product instanceof PromotionProduct;
    }

    private String[] parseData(String line, int expectedSize) {
        String[] data = line.split(",");
        if (data.length != expectedSize) {
            throw new IllegalArgumentException("[ERROR] 데이터 형식이 잘못되었습니다.");
        }

        return Arrays.stream(data).map(String::trim).toArray(String[]::new);
    }
}
