package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.PromotionResult;
import store.domain.Receipt;

public class CheckoutService {
    private final PromotionService promotionService;
    private final MembershipService membershipService;

    public CheckoutService(PromotionService promotionService, MembershipService membershipService) {
        this.promotionService = promotionService;
        this.membershipService = membershipService;
    }

    public Receipt checkout(Map<Product, Integer> cartItems, Map<Product, Integer> originalQuantities,
                            boolean applyMembership) {
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

            PromotionResult promoResult = promotionService.applyPromotion(product, originalQuantity);

            if (promoResult.isPromotionApplied()) {
                int promoAvailableQuantity = promoResult.getPromoAvailableQuantity();
                int promoAmount = product.getPrice() * promoAvailableQuantity;

                totalAmount += promoAmount;
                promotionDiscount += promoResult.getDiscountAmount();
                finalAmount += promoResult.getFinalAmount();

                purchaseDetails.add(
                        String.format("%s\t%d\t%d", product.getName(), totalOriginalQuantity, totalOriginalAmount));

                if (promoResult.getFreeQuantity() > 0) {
                    freeItems.add(String.format("%s\t%d", product.getName(), promoResult.getFreeQuantity()));
                }

                if (applyMembership && promoResult.getNonPromoQuantity() > 0) {
                    int nonPromoQuantity = promoResult.getNonPromoQuantity();
                    int nonPromoAmount = product.getPrice() * nonPromoQuantity;
                    int discount = membershipService.applyMembershipDiscount(nonPromoAmount);
                    membershipDiscount += discount;
                    finalAmount -= discount;

                    purchaseDetails.add(
                            String.format("%s\t%d\t%d", product.getName(), nonPromoQuantity, nonPromoAmount));
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

        return new Receipt(purchaseDetails, freeItems, totalOriginalQuantity, totalOriginalAmount, totalAmount,
                promotionDiscount, membershipDiscount, finalAmount);
    }
}
