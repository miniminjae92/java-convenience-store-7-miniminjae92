package store.repository;

import store.domain.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegularInventory {
    private final Map<String, Product> regularProducts = new LinkedHashMap<>();

    public void addProduct(Product product) {
        regularProducts.put(product.getName(), product);
    }

    public Product findByName(String name) {
        return regularProducts.get(name);
    }

    public void updateProduct(Product product) {
        regularProducts.put(product.getName(), product);
    }

    public Map<String, Product> getAllProducts() {
        return new LinkedHashMap<>(regularProducts);
    }
}
