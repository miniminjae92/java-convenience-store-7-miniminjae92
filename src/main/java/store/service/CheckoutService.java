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

    public CheckoutService(ProductService productService, PromotionService promotionService,
                           MembershipService membershipService) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.membershipService = membershipService;
    }

    public Receipt checkout(Map<Product, Integer> cartItems, boolean applyMembership) {
        int totalAmount = 0;
        int promotionDiscount = 0;
        int membershipDiscount = 0;
        int finalAmount = 0;

        int totalQuantity = 0; // 실제 구매 의도한 총 수량
        List<String> purchaseDetails = new ArrayList<>();
        List<String> freeItems = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int originalQuantity = entry.getValue();  // 사용자가 요청한 원래 수량
            int originalAmount = product.getPrice() * originalQuantity;

            totalQuantity += originalQuantity; // 요청된 원래 총 수량을 합산

            PromotionResult promoResult = promotionService.applyPromotion(product, originalQuantity);

            if (promoResult.isPromotionApplied()) {
                int promoAvailableQuantity = promoResult.getPromoAvailableQuantity();
                int promoAmount = product.getPrice() * promoAvailableQuantity;

                totalAmount += promoAmount;
                promotionDiscount += promoResult.getDiscountAmount();
                finalAmount += promoResult.getFinalAmount();

                purchaseDetails.add(
                        String.format("%s\t%d\t%d", product.getName(), promoAvailableQuantity, promoAmount));

                if (promoResult.getFreeQuantity() > 0) {
                    freeItems.add(String.format("%s\t%d개", product.getName(), promoResult.getFreeQuantity()));
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

                productService.decreaseProductStock(product.getName(), originalQuantity);

            } else {
                int amount = product.getPrice() * originalQuantity;
                totalAmount += amount;
                finalAmount += amount;

                if (applyMembership) {
                    int discount = membershipService.applyMembershipDiscount(amount);
                    membershipDiscount += discount;
                    finalAmount -= discount;
                }

                purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), originalQuantity, amount));
                productService.decreaseProductStock(product.getName(), originalQuantity);
            }
        }

        // 총 구매 수량은 원래 구매 수량(totalQuantity)을 사용
        return new Receipt(purchaseDetails, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount,
                totalQuantity);
    }
}