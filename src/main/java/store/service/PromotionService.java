package store.service;

import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionResult;
import store.repository.PromotionRepository;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public PromotionResult applyPromotion(Product product, int quantity) {
        if (!isPromotionApplicable(product)) {
            return PromotionResult.noPromotion(product.getPrice() * quantity, quantity);
        }

        Promotion promotion = promotionRepository.findByType(product.getPromotionType());
        if (!isActivePromotion(promotion)) {
            return PromotionResult.noPromotion(product.getPrice() * quantity, quantity);
        }

        int payableQuantity = promotion.calculatePayableQuantity(quantity);
        int freeQuantity = promotion.calculateFreeQuantity(quantity);

        int totalPromoItemsNeeded = payableQuantity + freeQuantity;
        int availableStock = product.getStock();

        boolean hasInsufficientPromoStock = availableStock < totalPromoItemsNeeded;
        int promoAvailableQuantity = calculatePromoAvailableQuantity(payableQuantity, availableStock, freeQuantity);
        int nonPromoQuantity = quantity - promoAvailableQuantity;

        int additionalQuantityNeeded = calculateAdditionalQuantityNeeded(quantity, promotion);
        boolean needsAdditionalPurchase = additionalQuantityNeeded > 0;

        int discountAmount = freeQuantity * product.getPrice();
        int finalAmount = promoAvailableQuantity * product.getPrice();

        return PromotionResult.withPromotion(
                needsAdditionalPurchase,
                hasInsufficientPromoStock,
                additionalQuantityNeeded,
                nonPromoQuantity,
                promoAvailableQuantity,
                freeQuantity,
                discountAmount,
                finalAmount,
                quantity
        );
    }

    private boolean isPromotionApplicable(Product product) {
        return product.getPromotionType() != null && !product.getPromotionType().equalsIgnoreCase("null");
    }

    private boolean isActivePromotion(Promotion promotion) {
        return promotion != null && promotion.isActive();
    }

    private int calculatePromoAvailableQuantity(int payableQuantity, int availableStock, int freeQuantity) {
        return Math.min(payableQuantity, availableStock - freeQuantity);
    }

    private int calculateAdditionalQuantityNeeded(int quantity, Promotion promotion) {
        int remainingQuantity = quantity % (promotion.getBuyQuantity() + promotion.getFreeQuantity());
        if (remainingQuantity > 0 && remainingQuantity < promotion.getBuyQuantity()) {
            return promotion.getBuyQuantity() - remainingQuantity;
        }
        return 0;
    }
}
