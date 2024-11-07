package store.domain;

import java.util.Map;

public class Checkout {

    public int calculateTotalPurchaseAmount(Cart cart) {
        int totalPurchaseAmount = 0;

        for (Map.Entry<Product, Integer> entry :  cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPurchaseAmount += product.getPrice() * quantity;
        }
        return totalPurchaseAmount;
    }
}
