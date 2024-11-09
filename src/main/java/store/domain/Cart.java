package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> items = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) {
        validateQuantity(quantity);
        int currentQty = items.getOrDefault(product, 0);
        items.put(product, currentQty + quantity);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 0보다 커야 합니다.");
        }
    }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }
}
