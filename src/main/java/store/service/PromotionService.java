package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.PromotionRepository;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public int applyPromotions(Cart cart) {
        int totalDiscount = 0;
        for (Product product : cart.getProducts()) {
            totalDiscount += applyPromotionToProduct(cart, product);
        }
        return totalDiscount;
    }

    private int applyPromotionToProduct(Cart cart, Product product) {
        Promotion promotion = getActivePromotion(product);
        if (promotion == null) {
            return 0;
        }
        int freeQuantity = calculateFreeQuantity(promotion, cart.getQuantity(product));
        cart.addFreeItem(product, freeQuantity);
        return freeQuantity * product.getPrice();
    }

    private Promotion getActivePromotion(Product product) {
        return promotionRepository.findByName(product.getPromotionType())
                .filter(promo -> promo.isActive(DateTimes.now()))
                .orElse(null);
    }

    private int calculateFreeQuantity(Promotion promotion, int quantity) {
        return promotion.calculateFreeQuantity(quantity);
    }
}
