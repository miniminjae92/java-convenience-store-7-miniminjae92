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
        if (product.getPromotionType() == null || product.getPromotionType().equalsIgnoreCase("null")) {
            return PromotionResult.noPromotion(product.getPrice() * quantity);
        }

        Promotion promotion = promotionRepository.findByType(product.getPromotionType());
        if (promotion == null || !promotion.isActive()) {
            return PromotionResult.noPromotion(product.getPrice() * quantity);
        }

        int payableQuantity = promotion.calculatePayableQuantity(quantity);
        int freeQuantity = promotion.calculateFreeQuantity(quantity);

        int totalPromoItemsNeeded = payableQuantity + freeQuantity;
        int availableStock = product.getStock();

        boolean hasInsufficientPromoStock = availableStock < totalPromoItemsNeeded;
        int nonPromoQuantity = 0;
        int promoAvailableQuantity = payableQuantity;

        if (hasInsufficientPromoStock) {
            promoAvailableQuantity = Math.min(payableQuantity, availableStock - freeQuantity);
            nonPromoQuantity = quantity - promoAvailableQuantity;
        }

        boolean needsAdditionalPurchase = false;
        int additionalQuantityNeeded = 0;
        int remainingQuantity = quantity % (promotion.getBuyQuantity() + promotion.getFreeQuantity());

        if (remainingQuantity > 0 && remainingQuantity < promotion.getBuyQuantity()) {
            needsAdditionalPurchase = true;
            additionalQuantityNeeded = promotion.getBuyQuantity() - remainingQuantity;
        }

        int discountAmount = freeQuantity * product.getPrice();
        int finalAmount = promoAvailableQuantity * product.getPrice();

        // 원래 수량을 포함한 PromotionResult 생성
        return PromotionResult.withPromotion(needsAdditionalPurchase, hasInsufficientPromoStock,
                additionalQuantityNeeded, nonPromoQuantity, promoAvailableQuantity,
                freeQuantity, discountAmount, finalAmount, quantity);
    }
}
