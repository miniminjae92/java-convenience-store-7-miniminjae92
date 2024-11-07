package store.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public Integer getQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }
}
