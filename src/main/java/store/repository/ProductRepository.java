package store.repository;

import store.domain.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Integer> generalStock = new HashMap<>();
    private final Map<String, Integer> promotionStock = new HashMap<>();

    public void addProduct(Product product, int quantity, boolean isPromotion) {
        String name = product.getName();
        products.putIfAbsent(name, product);

        if (!isPromotion) {
            addGeneralStock(name, quantity);
        }

        if (isPromotion) {
            addPromotionStock(name, quantity);
        }
    }

    private void addGeneralStock(String name, int quantity) {
        generalStock.put(name, generalStock.getOrDefault(name, 0) + quantity);
    }

    private void addPromotionStock(String name, int quantity) {
        promotionStock.put(name, promotionStock.getOrDefault(name, 0) + quantity);
    }

    public Product getProduct(String name) {
        if (!products.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
        }
        return products.get(name);
    }

    public int getGeneralStock(String name) {
        return generalStock.getOrDefault(name, 0);
    }

    public int getPromotionStock(String name) {
        return promotionStock.getOrDefault(name, 0);
    }

    public void reduceStock(String name, int quantity, boolean isPromotion) {
        if (!isPromotion) {
            reduceGeneralStock(name, quantity);
        }

        if (isPromotion) {
            reducePromotionStock(name, quantity);
        }
    }

    private void reduceGeneralStock(String name, int quantity) {
        int currentQty = generalStock.getOrDefault(name, 0);
        if (currentQty < quantity) {
            throw new IllegalStateException("[ERROR] 일반 재고가 부족합니다.");
        }
        generalStock.put(name, currentQty - quantity);
    }

    private void reducePromotionStock(String name, int quantity) {
        int currentQty = promotionStock.getOrDefault(name, 0);
        if (currentQty < quantity) {
            throw new IllegalStateException("[ERROR] 프로모션 재고가 부족합니다.");
        }
        promotionStock.put(name, currentQty - quantity);
    }

    public Map<String, Product> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
