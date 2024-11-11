package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.PromotionResult;
import store.domain.Receipt;

public class CheckoutService {
    private final ProductService productService;
    private final PromotionService promotionService;
    private final MembershipService membershipService;

    public CheckoutService(ProductService productService, PromotionService promotionService, MembershipService membershipService) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.membershipService = membershipService;
    }

    public Receipt checkout(Map<Product, Integer> cartItems, Map<Product, Integer> originalQuantities, boolean applyMembership) {
        int totalAmount = 0;
        int promotionDiscount = 0;
        int membershipDiscount = 0;
        int finalAmount = 0;

        int totalOriginalQuantity = 0;
        int totalOriginalAmount = 0;

        List<String> purchaseDetails = new ArrayList<>();
        List<String> freeItems = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int adjustedQuantity = entry.getValue();
            int originalQuantity = originalQuantities.getOrDefault(product, 0);
            int originalAmount = product.getPrice() * originalQuantity;

            totalOriginalQuantity += originalQuantity;
            totalOriginalAmount += originalAmount;

            productService.decreaseProductStock(product.getName(), adjustedQuantity);

            PromotionResult promoResult = promotionService.applyPromotion(product, originalQuantity);

            if (promoResult.isPromotionApplied()) {
                int promoAvailableQuantity = promoResult.getPromoAvailableQuantity();
                int promoAmount = product.getPrice() * promoAvailableQuantity;

                totalAmount += promoAmount;
                promotionDiscount += promoResult.getDiscountAmount();
                finalAmount += promoResult.getFinalAmount();

                // 구매 내역에 프로모션 적용된 수량 추가
                purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), promoAvailableQuantity, promoAmount));

                // 무료 증정 상품 추가
                if (promoResult.getFreeQuantity() > 0) {
                    freeItems.add(String.format("%s\t%d개", product.getName(), promoResult.getFreeQuantity()));
                }

                // 멤버십 할인이 적용된 경우
                if (applyMembership && promoResult.getNonPromoQuantity() > 0) {
                    int nonPromoQuantity = promoResult.getNonPromoQuantity();
                    int nonPromoAmount = product.getPrice() * nonPromoQuantity;
                    int discount = membershipService.applyMembershipDiscount(nonPromoAmount);
                    membershipDiscount += discount;
                    finalAmount -= discount;

                    // 비할인 상품도 구매 내역에 추가
                    purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), nonPromoQuantity, nonPromoAmount));
                    totalAmount += nonPromoAmount;
                }

            } else {
                int amount = product.getPrice() * adjustedQuantity;
                totalAmount += amount;
                finalAmount += amount;

                if (applyMembership) {
                    int discount = membershipService.applyMembershipDiscount(amount);
                    membershipDiscount += discount;
                    finalAmount -= discount;
                }

                purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), adjustedQuantity, amount));
            }
        }

        return new Receipt(
                purchaseDetails,
                freeItems,
                totalOriginalQuantity,
                totalOriginalAmount,
                totalAmount,
                promotionDiscount,
                membershipDiscount,
                finalAmount
        );
    }
}
