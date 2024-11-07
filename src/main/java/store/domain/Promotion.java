package store.domain;

import java.time.LocalDateTime;
import java.util.Map;

public class Promotion {

    private String promotionName;
    private int buy;
    private int get;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(String promotionName, int buy, int get, String startDate, String endDate) {
        this.promotionName = promotionName;
        this.buy = buy;
        this.get = get;
        this.startDate = LocalDateTime.parse(startDate);
        this.endDate = LocalDateTime.parse(endDate);
    }

    public int apply(Cart cart, LocalDateTime currentTime) {
        if (!currentTime.isBefore(startDate) && !currentTime.isAfter(endDate)) {
            int totalAmount = 0;
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                int quantity = entry.getValue();
                totalAmount += entry.getKey().getPrice() * calculatePayableItems(quantity);;
            }
            return totalAmount;
        }

        return cart.getItems().entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    private int calculatePayableItems(int quantity) {
        int totalPromotionUnit = buy + get; // 프로모션 단위 (예: 1+1이면 2)
        int promotionCount = quantity / totalPromotionUnit; // 프로모션 적용 횟수
        int remainingItems = quantity % totalPromotionUnit; // 프로모션 단위로 나누어 떨어지지 않는 남은 수량

        return (promotionCount * buy) + remainingItems; // 총 결제 수량
    }

}
