package store.controller;

import store.common.ErrorMessage;
import store.common.OrderParser;
import store.domain.Product;
import store.domain.PromotionResult;
import store.domain.Receipt;
import store.service.CartService;
import store.service.CheckoutService;
import store.service.ProductService;
import store.service.PromotionService;
import store.validator.InputValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.HashMap;
import java.util.Map;

public class StoreController {
    private final ProductService productService;
    private final CartService cartService;
    private final CheckoutService checkoutService;
    private final PromotionService promotionService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(ProductService productService, CartService cartService,
                           CheckoutService checkoutService, PromotionService promotionService,
                           InputView inputView, OutputView outputView) {
        this.productService = productService;
        this.cartService = cartService;
        this.checkoutService = checkoutService;
        this.promotionService = promotionService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        boolean continueShopping = true;
        while (continueShopping) {
            displayAvailableProducts();
            processCartInput();
            handlePromotions();
            boolean applyMembership = applyMembership();
            checkoutCart(applyMembership);
            continueShopping = promptContinueShopping();
        }
    }

    private void displayAvailableProducts() {
        outputView.displayWelcomeMessage();
        outputView.displayStockInfoHeader();
        outputView.displayProducts(productService.getAllProducts());
    }

    private void processCartInput() {
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                String input = getValidatedInput();
                Map<String, Integer> selectedProducts = OrderParser.parseProductInput(input);

                for (Map.Entry<String, Integer> entry : selectedProducts.entrySet()) {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();

                    Product product = productService.findProductByName(productName);
                    if (product == null) {
                        throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PRODUCT.getMessage());
                    }

                    if (!productService.hasSufficientTotalStock(productName, quantity)) {
                        throw new IllegalArgumentException(ErrorMessage.EXCEEDS_STOCK_QUANTITY.getMessage());
                    }

                    cartService.addItemToCart(productName, quantity);
                }
                isValidInput = true;
            } catch (IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
            }
        }
    }

    private void handlePromotions() {
        Map<Product, Integer> originalCartItems = cartService.getOriginalItems();
        Map<Product, Integer> promoCartItems = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : originalCartItems.entrySet()) {
            Product product = entry.getKey();
            int originalQuantity = entry.getValue();

            PromotionResult promoResult = promotionService.applyPromotion(product, originalQuantity);

            if (promoResult.isPromotionApplied()) {
                int finalQuantity = promoResult.getPromoAvailableQuantity();
                promoCartItems.put(product, finalQuantity);


                if (promoResult.needsAdditionalPurchase()) {
                    boolean userAgrees = inputView.promptUserForAdditionalPurchase(product.getName(), promoResult.getAdditionalQuantityNeeded());
                    if (userAgrees) {
                        int updatedQuantity = finalQuantity + promoResult.getAdditionalQuantityNeeded();
                        promoCartItems.put(product, updatedQuantity);
                    } else {
                        int adjustedQuantity = (finalQuantity / (promoResult.getPromoAvailableQuantity())) * promoResult.getPromoAvailableQuantity();
                        promoCartItems.put(product, adjustedQuantity);
                    }
                }

                if (promoResult.hasInsufficientPromoStock()) {
                    boolean userAgrees = inputView.promptUserForRegularPricePurchase(product.getName(), promoResult.getNonPromoQuantity());
                    if (userAgrees) {
                        int updatedQuantity = finalQuantity + promoResult.getNonPromoQuantity();
                        promoCartItems.put(product, updatedQuantity);
                    } else {
                        promoCartItems.put(product, promoResult.getPromoAvailableQuantity());
                    }
                }
            } else {
                promoCartItems.put(product, originalQuantity);
            }
        }

        cartService.setPromoCartItems(promoCartItems);
    }

    private boolean applyMembership() {
        try {
            return inputView.promptMembership();
        } catch (IllegalArgumentException e) {
            outputView.displayError(e.getMessage());
            return false;
        }
    }

    private void checkoutCart(boolean applyMembership) {
        try {
            Map<Product, Integer> cartItems = cartService.getPromoItems();
            Map<Product, Integer> originalQuantities = cartService.getOriginalItems();
            Receipt receipt = checkoutService.checkout(cartItems, originalQuantities, applyMembership);
            outputView.displayReceipt(receipt);

            cartService.clearCart();
        } catch (Exception e) {
            outputView.displayError(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }

    private String getValidatedInput() {
        String input = inputView.readItem();
        System.out.println();
        InputValidator.validateFormat(input);
        return input;
    }

    private boolean promptContinueShopping() {
        return inputView.promptContinueShopping();
    }
}
