package store.service;

import store.domain.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    private Map<Product, Integer> originalCartItems = new LinkedHashMap<>();
    private Map<Product, Integer> promoCartItems = new LinkedHashMap<>();

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    // 원래 수량을 장바구니에 추가
    public void addItemToCart(String productName, int quantity) {
        Product product = productService.findProductByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + productName);
        }

        originalCartItems.put(product, originalCartItems.getOrDefault(product, 0) + quantity);
    }

    // 프로모션 적용 후 장바구니를 설정
    public void setPromoCartItems(Map<Product, Integer> promoItems) {
        this.promoCartItems = promoItems;
    }

    // 원래 수량을 반환
    public Map<Product, Integer> getOriginalItems() {
        return new LinkedHashMap<>(originalCartItems);
    }

    // 프로모션 적용 후 수량을 반환
    public Map<Product, Integer> getPromoItems() {
        return new LinkedHashMap<>(promoCartItems);
    }

    // 장바구니 초기화
    public void clearCart() {
        originalCartItems.clear();
        promoCartItems.clear();
    }
}
