package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private final String promotionType; // 예: "1+1", "2+1"
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String promotionType, int buyQuantity, int freeQuantity,
                     LocalDateTime startDate, LocalDateTime endDate) {
        validateQuantities(buyQuantity, freeQuantity);
        this.promotionType = promotionType;
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

    // 기존의 메서드들 유지 또는 필요에 따라 수정
    public String getPromotionType() { return promotionType; }
    public int getBuyQuantity() { return buyQuantity; }
    public int getFreeQuantity() { return freeQuantity; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }

    // 검증 메서드
    private void validatePromotionType(String promotionType) {
        if (promotionType == null || promotionType.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 프로모션 타입은 필수 입력 사항입니다.");
        }
    }

    private void validateQuantities(int buyQuantity, int freeQuantity) {
        if (buyQuantity <= 0 || freeQuantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 구매 수량과 증정 수량은 0보다 커야 합니다.");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("[ERROR] 프로모션 기간은 필수 입력 사항입니다.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("[ERROR] 프로모션 종료일은 시작일 이후여야 합니다.");
        }
    }
}
