package store.service;

import store.domain.PromotionResult;
import store.domain.Receipt;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckoutService {
    private final PromotionService promotionService;
    private final MembershipService membershipService;
    private final ProductService productService;

    public CheckoutService(PromotionService promotionService, MembershipService membershipService, ProductService productService) {
        this.promotionService = promotionService;
        this.membershipService = membershipService;
        this.productService = productService;
    }

    public Receipt checkout(Map<String, Integer> cartItems, boolean applyMembership) {
        int totalAmount = calculateTotalAmount(cartItems);
        int nonPromotionTotal = calculateNonPromotionTotal(cartItems);

        int promotionDiscount = applyPromotions(cartItems);
        int discountedAmount = totalAmount - promotionDiscount;
        int membershipDiscount = applyMembership ? membershipService.applyMembershipDiscount(nonPromotionTotal) : 0;
        int finalAmount = discountedAmount - membershipDiscount;

        List<String> purchaseDetails = generatePurchaseDetails(cartItems);
        List<String> freeItems = promotionService.getFreeItems();

        reduceStockForCartItems(cartItems);

        return new Receipt(purchaseDetails, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount);
    }

    private int calculateTotalAmount(Map<String, Integer> cartItems) {
        return cartItems.entrySet().stream()
                .mapToInt(entry -> productService.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    private int calculateNonPromotionTotal(Map<String, Integer> cartItems) {
        return cartItems.entrySet().stream()
                .filter(entry -> !promotionService.applyPromotion(entry.getKey(), entry.getValue()).isPromotionApplied())
                .mapToInt(entry -> productService.getProductPrice(entry.getKey()) * entry.getValue())
                .sum();
    }

    private int applyPromotions(Map<String, Integer> cartItems) {
        return cartItems.entrySet().stream()
                .mapToInt(entry -> promotionService.applyPromotion(entry.getKey(), entry.getValue()).getDiscountAmount())
                .sum();
    }

    private List<String> generatePurchaseDetails(Map<String, Integer> cartItems) {
        return cartItems.entrySet().stream()
                .map(entry -> String.format("%s\t%d\t%d",
                        entry.getKey(), entry.getValue(), productService.getProductPrice(entry.getKey()) * entry.getValue()))
                .collect(Collectors.toList());
    }

    private void reduceStockForCartItems(Map<String, Integer> cartItems) {
        cartItems.forEach((productName, quantity) -> {
            if (productName != null) {
                productService.validateAndReduceStock(productName, quantity);
            }
        });
    }
}
