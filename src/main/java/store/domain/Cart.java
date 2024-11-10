package store.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private final Map<Product, Integer> items = new LinkedHashMap<>();
    private final Map<Product, Integer> freeItems = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) {
        validateQuantity(quantity);
        int currentQty = items.getOrDefault(product, 0);
        items.put(product, currentQty + quantity);
    }

    public void updateItemQuantity(Product product, int newQuantity) {
        if (newQuantity <= 0) {
            items.remove(product);  // 수량이 0 이하이면 상품을 삭제
        } else {
            items.put(product, newQuantity);  // 수량을 새 값으로 업데이트
        }
    }

    public void addFreeItem(Product product, int quantity) {
        validateQuantity(quantity);
        int currentFreeQty = freeItems.getOrDefault(product, 0);
        freeItems.put(product, currentFreeQty + quantity);
    }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public Map<Product, Integer> getFreeItems() {
        return Collections.unmodifiableMap(freeItems);
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(items.keySet());
    }

    public int getQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }

    public void updateQuantityForPromotion(Product product, int quantity) {
        if (items.containsKey(product)) {
            items.put(product, quantity);
        }
    }

    public void clear() {
        items.clear();
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 0보다 커야 합니다.");
        }
    }
}
