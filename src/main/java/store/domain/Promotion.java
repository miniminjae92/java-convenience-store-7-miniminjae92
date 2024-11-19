package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.ErrorMessage;
import java.time.LocalDateTime;

public class Promotion {
    private final String promotionType;
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String promotionType, int buyQuantity, int freeQuantity,
                     LocalDateTime startDate, LocalDateTime endDate) {
        validatePromotionType(promotionType);
        validateQuantities(buyQuantity, freeQuantity);
        validateDates(startDate, endDate);
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


    public String getPromotionType() {
        return promotionType;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    private int[] calculatePromotion(int quantity) {
        int totalUnit = buyQuantity + freeQuantity;
        int promoSets = quantity / totalUnit;
        int remaining = quantity % totalUnit;
        int payableQuantity = promoSets * buyQuantity + remaining;
        int freeQuantityTotal = promoSets * freeQuantity;
        return new int[]{payableQuantity, freeQuantityTotal};
    }

    private void validatePromotionType(String promotionType) {
        if (promotionType == null || promotionType.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage());
        }
    }

    private void validateQuantities(int buyQuantity, int freeQuantity) {
        if (buyQuantity <= 0 || freeQuantity <= 0) {
            throw new IllegalArgumentException(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage());
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }
}
