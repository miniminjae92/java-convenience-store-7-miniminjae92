package store.repository;

import store.domain.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public void save(Product product) {
        products.put(product.getName(), product);
    }

    public Product findByName(String name) {
        Product product = products.get(name);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + name);
        }
        return product;
    }

    public Map<String, Product> findAll() {
        return new HashMap<>(products);
    }

    public void update(String name, Product updatedProduct) {
        if (products.containsKey(name)) {
            products.put(name, updatedProduct);
        } else {
            throw new IllegalStateException("[ERROR] 해당 이름의 상품이 존재하지 않습니다: " + name);
        }
    }

    public void delete(String name) {
        if (!products.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품이므로 삭제할 수 없습니다: " + name);
        }
        products.remove(name);
    }

    public void deleteAll() {
        products.clear();
    }
}
