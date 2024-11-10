package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name, int buyQuantity, int freeQuantity, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive() {
        LocalDateTime currentTime = DateTimes.now();
        return !currentTime.isBefore(startDate) && !currentTime.isAfter(endDate);
    }

    public int calculatePayableQuantity(int quantity) {
        return calculatePromotion(quantity)[0];
    }

    public int calculateFreeQuantity(int quantity) {
        return calculatePromotion(quantity)[1];
    }

    private int[] calculatePromotion(int quantity) {
        int totalUnit = buyQuantity + freeQuantity;
        int promoSets = quantity / totalUnit;
        int remaining = quantity % totalUnit;
        int payableQuantity = promoSets * buyQuantity + remaining;
        int freeQuantityTotal = promoSets * freeQuantity;
        return new int[]{payableQuantity, freeQuantityTotal};
    }

    private void validateQuantities(int buyQuantity, int freeQuantity) {
        if (buyQuantity <= 0 || freeQuantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 프로모션 수량은 0보다 커야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
