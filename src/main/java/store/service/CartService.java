package store.service;

import java.util.Map.Entry;
import store.domain.Cart;
import store.domain.Product;
import store.repository.ProductRepository;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.stream.Collectors;

public class CartService {
    private final Cart cart;
    private final ProductRepository productRepository;

    public CartService(Cart cart, ProductRepository productRepository) {
        this.cart = cart;
        this.productRepository = productRepository;
    }

    public void addItemToCart(String productName, int quantity) {
        Product product = productRepository.findByName(productName);
        cart.addItem(product, quantity);
    }

    public void updateItemQuantity(String productName, int quantity) {
        Product product = productRepository.findByName(productName);
        cart.updateItemQuantity(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return cart.getItems();
    }

    public Map<String, Integer> getCartItems() {
        return cart.getItems().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(), // 이름만 키로 사용
                        Entry::getValue,
                        Integer::sum, // 중복 키 발생 시 수량을 합산
                        LinkedHashMap::new // 순서를 유지하는 LinkedHashMap 사용
                ));
    }

    public void clearCart() {
        cart.clear();
    }
}
