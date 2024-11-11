// PromotionInventory.java
package store.repository;

import store.domain.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class PromotionInventory {
    private final Map<String, Product> promotionProducts = new LinkedHashMap<>();

    public void addProduct(Product product) {
        String key = product.getName() + "-" + product.getPromotionType();
        promotionProducts.put(key, product);
    }

    public Product findByName(String name) {
        for (Product product : promotionProducts.values()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public void updateProduct(Product product) {
        String key = product.getName() + "-" + product.getPromotionType();
        promotionProducts.put(key, product);
    }

    public Map<String, Product> getAllProducts() {
        return new LinkedHashMap<>(promotionProducts);
    }
}
