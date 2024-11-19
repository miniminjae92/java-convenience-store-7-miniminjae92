package store.service;

import store.common.ErrorMessage;
import store.domain.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    private final Map<Product, Integer> originalCartItems = new LinkedHashMap<>();
    private Map<Product, Integer> promoCartItems = new LinkedHashMap<>();

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addItemToCart(String productName, int quantity) {
        Product product = productService.findProductByName(productName);
        if (product == null) {
            throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PRODUCT.getMessage());
        }

        originalCartItems.put(product, originalCartItems.getOrDefault(product, 0) + quantity);
    }

    public void setPromoCartItems(Map<Product, Integer> promoItems) {
        this.promoCartItems = promoItems;
    }

    public Map<Product, Integer> getOriginalItems() {
        return new LinkedHashMap<>(originalCartItems);
    }

    public Map<Product, Integer> getPromoItems() {
        return new LinkedHashMap<>(promoCartItems);
    }

    public void clearCart() {
        originalCartItems.clear();
        promoCartItems.clear();
    }
}
