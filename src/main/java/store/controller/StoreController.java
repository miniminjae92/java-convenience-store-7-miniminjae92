package store.controller;

import java.time.LocalDate;
import store.common.OrderParser;
import store.domain.PromotionResult;
import store.domain.Receipt;
import store.service.CheckoutService;
import store.service.ProductService;
import store.service.CartService;
import store.service.PromotionService;
import store.validator.InputValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class StoreController {
    private final ProductService productService;
    private final CartService cartService;
    private final CheckoutService checkoutService;
    private final PromotionService promotionService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(ProductService productService, CartService cartService, CheckoutService checkoutService, PromotionService promotionService, InputView inputView, OutputView outputView) {
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
                addProductsToCart(selectedProducts);
                isValidInput = true;
            } catch (IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
            }
        }
    }

    private void handlePromotions() {
        cartService.getItems().forEach((product, quantity) -> {
            PromotionResult result = promotionService.applyPromotion(product.getName(), quantity);

            if (result.needsAdditionalPurchase()) {
                boolean userWantsToAdd = inputView.promptUserForAdditionalPurchase(
                        product.getName(), result.getAdditionalQuantityNeeded());
                if (userWantsToAdd) {
                    cartService.addItemToCart(product.getName(), result.getAdditionalQuantityNeeded());
                }
            }

            if (result.hasInsufficientPromoStock()) {
                boolean userWantsToPayRegularPrice = inputView.promptUserForRegularPricePurchase(
                        product.getName(), result.getNonPromoQuantity());
                if (!userWantsToPayRegularPrice) {
                    cartService.updateItemQuantity(product.getName(), product.getStock());
                }
            }
        });
    }

    private boolean applyMembership() {
        boolean isValidMembership = false;
        boolean applyMembership = false;
        while (!isValidMembership) {
            try {
                applyMembership = inputView.promptMembership();
                isValidMembership = true;
            } catch (IllegalArgumentException e) {
                outputView.displayError("멤버십 적용에 실패했습니다. 다시 시도해 주세요.");
            }
        }
        return applyMembership;
    }

    private void checkoutCart(boolean applyMembership) {
        boolean isCheckoutSuccessful = false;
        while (!isCheckoutSuccessful) {
            try {
                Map<String, Integer> cartItems = cartService.getCartItems();
                Receipt receipt = checkoutService.checkout(cartItems, applyMembership);
                outputView.displayReceipt(receipt);
                cartService.clearCart();
                isCheckoutSuccessful = true;
            } catch (Exception e) {
                outputView.displayError("결제 처리 중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
        }
    }

    private String getValidatedInput() {
        String input = inputView.readItem();
        InputValidator.validateFormat(input);
        return input;
    }

    private void addProductsToCart(Map<String, Integer> selectedProducts) {
        for (Map.Entry<String, Integer> entry : selectedProducts.entrySet()) {
            cartService.addItemToCart(entry.getKey(), entry.getValue());
        }
    }

    private boolean promptContinueShopping() {
        return inputView.promptContinueShopping();
    }
}
