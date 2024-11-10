package store.domain;

public class PromotionResult {
    private final boolean promotionApplied;
    private final boolean needsAdditionalPurchase;
    private final boolean hasInsufficientPromoStock;
    private final int additionalQuantityNeeded;
    private final int nonPromoQuantity;
    private final int freeQuantity;
    private final int discountAmount;
    private final int finalAmount;

    public PromotionResult(boolean promotionApplied, boolean needsAdditionalPurchase, boolean hasInsufficientPromoStock,
                           int additionalQuantityNeeded, int nonPromoQuantity, int freeQuantity, int discountAmount, int finalAmount) {
        this.promotionApplied = promotionApplied;
        this.needsAdditionalPurchase = needsAdditionalPurchase;
        this.hasInsufficientPromoStock = hasInsufficientPromoStock;
        this.additionalQuantityNeeded = additionalQuantityNeeded;
        this.nonPromoQuantity = nonPromoQuantity;
        this.freeQuantity = freeQuantity;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
    }

    public boolean isPromotionApplied() { return promotionApplied; }
    public boolean needsAdditionalPurchase() { return needsAdditionalPurchase; }
    public boolean hasInsufficientPromoStock() { return hasInsufficientPromoStock; }
    public int getAdditionalQuantityNeeded() { return additionalQuantityNeeded; }
    public int getNonPromoQuantity() { return nonPromoQuantity; }
    public int getFreeQuantity() { return freeQuantity; }
    public int getDiscountAmount() { return discountAmount; }
    public int getFinalAmount() { return finalAmount; }

    public static PromotionResult noPromotion(int totalAmount) {
        // 프로모션이 없는 경우 총 금액 그대로 반환
        return new PromotionResult(false, false, false, 0, 0, 0, 0, totalAmount);
    }

    public static PromotionResult withPromotion(int discountAmount, int finalAmount, int freeQuantity) {
        // 프로모션이 적용된 경우의 결과 반환
        return new PromotionResult(true, false, false, 0, 0, freeQuantity, discountAmount, finalAmount);
    }
}
