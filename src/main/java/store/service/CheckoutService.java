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
                handlePromotionAppliedProduct(promoResult, product, applyMembership, purchaseDetails, freeItems);
                int promoAmount = product.getPrice() * promoResult.getPromoAvailableQuantity();

                totalAmount += promoAmount;
                promotionDiscount += promoResult.getDiscountAmount();
                finalAmount += promoResult.getFinalAmount();
                continue;
            }

            handleRegularProduct(product, adjustedQuantity, applyMembership, purchaseDetails);
            int amount = product.getPrice() * adjustedQuantity;

            totalAmount += amount;
            finalAmount += amount;

            if (applyMembership) {
                int discount = membershipService.applyMembershipDiscount(amount);
                membershipDiscount += discount;
                finalAmount -= discount;
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

    private void handlePromotionAppliedProduct(PromotionResult promoResult, Product product, boolean applyMembership, List<String> purchaseDetails, List<String> freeItems) {
        int promoAvailableQuantity = promoResult.getPromoAvailableQuantity();
        int promoAmount = product.getPrice() * promoAvailableQuantity;

        purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), promoAvailableQuantity, promoAmount));

        if (promoResult.getFreeQuantity() > 0) {
            freeItems.add(String.format("%s\t%d개", product.getName(), promoResult.getFreeQuantity()));
        }

        if (applyMembership && promoResult.getNonPromoQuantity() > 0) {
            int nonPromoQuantity = promoResult.getNonPromoQuantity();
            int nonPromoAmount = product.getPrice() * nonPromoQuantity;
            int discount = membershipService.applyMembershipDiscount(nonPromoAmount);

            purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), nonPromoQuantity, nonPromoAmount));
        }
    }

    private void handleRegularProduct(Product product, int adjustedQuantity, boolean applyMembership, List<String> purchaseDetails) {
        int amount = product.getPrice() * adjustedQuantity;

        purchaseDetails.add(String.format("%s\t%d\t%d", product.getName(), adjustedQuantity, amount));

        if (applyMembership) {
            membershipService.applyMembershipDiscount(amount);
        }
    }
}
