package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<String, Integer> items = new LinkedHashMap<>();

    public void addItem(String productName, int quantity) {
        validateProductName(productName);
        validateQuantity(quantity);
        int currentQty = items.getOrDefault(productName, 0);
        items.put(productName, currentQty + quantity);
    }

    private void validateProductName(String productName) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 0보다 커야 합니다.");
        }
    }

    public Map<String, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }
}
