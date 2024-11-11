// RegularInventory.java
package store.repository;

import store.domain.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegularInventory {
    private final Map<String, Product> regularProducts = new LinkedHashMap<>();

    public void addProduct(Product product) {
        String key = product.getName() + "-null";
        regularProducts.put(key, product);
    }

    public Product findByName(String name) {
        return regularProducts.get(name + "-null");
    }

    public void updateProduct(Product product) {
        String key = product.getName() + "-null";
        regularProducts.put(key, product);
    }

    public Map<String, Product> getAllProducts() {
        return new LinkedHashMap<>(regularProducts);
    }
}
