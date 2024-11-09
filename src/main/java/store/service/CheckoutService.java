package store.service;

import store.domain.Cart;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class CheckoutService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public CheckoutService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public int calculateTotalAmount(Cart cart) {
        return cart.getItems().entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}


