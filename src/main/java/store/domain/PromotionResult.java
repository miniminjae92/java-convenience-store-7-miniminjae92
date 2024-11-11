// PromotionResult.java
package store.domain;

public class PromotionResult {
    private final boolean promotionApplied;
    private final boolean needsAdditionalPurchase;
    private final boolean hasInsufficientPromoStock;
    private final int additionalQuantityNeeded;
    private final int nonPromoQuantity;
    private final int promoAvailableQuantity;
    private final int freeQuantity;
    private final int discountAmount;
    private final int finalAmount;
    private final int originalQuantity; // 원래 수량 필드 추가

    public PromotionResult(boolean promotionApplied, boolean needsAdditionalPurchase, boolean hasInsufficientPromoStock,
                           int additionalQuantityNeeded, int nonPromoQuantity, int promoAvailableQuantity,
                           int freeQuantity, int discountAmount, int finalAmount, int originalQuantity) { // 원래 수량 포함
        this.promotionApplied = promotionApplied;
        this.needsAdditionalPurchase = needsAdditionalPurchase;
        this.hasInsufficientPromoStock = hasInsufficientPromoStock;
        this.additionalQuantityNeeded = additionalQuantityNeeded;
        this.nonPromoQuantity = nonPromoQuantity;
        this.promoAvailableQuantity = promoAvailableQuantity;
        this.freeQuantity = freeQuantity;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.originalQuantity = originalQuantity; // 초기화
    }

    // 게터 메서드 추가
    public boolean isPromotionApplied() { return promotionApplied; }
    public boolean needsAdditionalPurchase() { return needsAdditionalPurchase; }
    public boolean hasInsufficientPromoStock() { return hasInsufficientPromoStock; }
    public int getAdditionalQuantityNeeded() { return additionalQuantityNeeded; }
    public int getNonPromoQuantity() { return nonPromoQuantity; }
    public int getPromoAvailableQuantity() { return promoAvailableQuantity; }
    public int getFreeQuantity() { return freeQuantity; }
    public int getDiscountAmount() { return discountAmount; }
    public int getFinalAmount() { return finalAmount; }
    public int getOriginalQuantity() { return originalQuantity; } // 원래 수량 게터 추가

    // 프로모션이 없는 경우의 정적 팩토리 메서드
    public static PromotionResult noPromotion(int totalAmount) {
        return new PromotionResult(false, false, false, 0, 0, 0, 0, 0, totalAmount, 0);
    }

    // 프로모션이 적용된 경우의 정적 팩토리 메서드
    public static PromotionResult withPromotion(boolean needsAdditionalPurchase, boolean hasInsufficientPromoStock,
                                                int additionalQuantityNeeded, int nonPromoQuantity, int promoAvailableQuantity,
                                                int freeQuantity, int discountAmount, int finalAmount, int originalQuantity) {
        return new PromotionResult(true, needsAdditionalPurchase, hasInsufficientPromoStock,
                additionalQuantityNeeded, nonPromoQuantity, promoAvailableQuantity,
                freeQuantity, discountAmount, finalAmount, originalQuantity); // 원래 수량 포함
    }
}
