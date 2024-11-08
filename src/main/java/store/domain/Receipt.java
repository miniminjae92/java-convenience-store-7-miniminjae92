package store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {
    private final List<String> purchaseDetails = new ArrayList<>();
    private final List<String> freeItems = new ArrayList<>();
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;

    // @param detail "상품명\t수량\t금액" 형식의 문자열
    public void addPurchaseDetail(String detail) {
        purchaseDetails.add(detail);
    }

    // @param item "상품명\t수량" 형식의 문자열
    public void addFreeItem(String item) {
        freeItems.add(item);
    }

    public void addTotalAmount(int amount) {
        this.totalAmount += amount;
    }

    public void addPromotionDiscount(int discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("[ERROR] 할인 금액은 음수가 될 수 없습니다.");
        }
        this.promotionDiscount += discount;
    }

    public void applyMembershipDiscount(int discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("[ERROR] 할인 금액은 음수가 될 수 없습니다.");
        }
        this.membershipDiscount = discount;
    }

    public List<String> getPurchaseDetails() {
        return Collections.unmodifiableList(purchaseDetails);
    }

    public List<String> getFreeItems() {
        return Collections.unmodifiableList(freeItems);
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int calculateFinalAmount() {
        return totalAmount - promotionDiscount - membershipDiscount;
    }

    public int getTotalQuantity() {
        return purchaseDetails.stream()
                .mapToInt(detail -> Integer.parseInt(detail.split("\t\t")[1]))
                .sum();
    }
}
