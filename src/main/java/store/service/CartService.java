package store.service;

import store.domain.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    private Map<Product, Integer> cartItems = new LinkedHashMap<>();
    private Map<Product, Integer> originalQuantities = new LinkedHashMap<>(); // 원래 수량을 저장하는 필드 추가

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addItemToCart(String productName, int quantity) {
        Product product = productService.findProductByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + productName);
        }

        // 장바구니와 원래 수량을 모두 업데이트
        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
        originalQuantities.put(product, originalQuantities.getOrDefault(product, 0) + quantity); // 원래 수량도 저장
    }

    public Map<Product, Integer> getItems() {
        return new LinkedHashMap<>(cartItems);
    }

    public Map<Product, Integer> getOriginalQuantities() {
        return new LinkedHashMap<>(originalQuantities); // 원래 수량을 반환하는 메서드 추가
    }

    public void setItems(Map<Product, Integer> items) {
        this.cartItems = items;
    }

    public void clearCart() {
        cartItems.clear();
        originalQuantities.clear(); // 원래 수량도 초기화
    }
}
