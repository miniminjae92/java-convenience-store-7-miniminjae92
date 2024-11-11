import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<String, Integer> items = new HashMap<>();

    public void addItem(String productName, int quantity) {
        items.put(productName, items.getOrDefault(productName, 0) + quantity);
    }

    public void removeItem(String productName) {
        items.remove(productName);
    }

    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }

    public void clear() {
        items.clear();
    }
}
