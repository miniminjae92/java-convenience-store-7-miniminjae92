package store.domain;

import java.time.LocalDateTime;

public class Promotion {
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(int buyQuantity, int freeQuantity, LocalDateTime startDate, LocalDateTime endDate) {
        validateQuantities(buyQuantity, freeQuantity);
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateQuantities(int buyQuantity, int freeQuantity) {
        if (buyQuantity <= 0 || freeQuantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 프로모션 수량은 0보다 커야 합니다.");
        }
    }

    public boolean isActive(LocalDateTime currentTime) {
        return !currentTime.isBefore(startDate) && !currentTime.isAfter(endDate);
    }

    public int[] calculatePromotion(int quantity) {
        int totalUnit = buyQuantity + freeQuantity;
        int promoSets = quantity / totalUnit;
        int remaining = quantity % totalUnit;
        int payableQuantity = promoSets * buyQuantity + remaining;
        int freeQuantityTotal = promoSets * freeQuantity;
        return new int[]{payableQuantity, freeQuantityTotal};
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }
}
