package store.controller;

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

                // 재고 확인 후 장바구니에 추가
                for (Map.Entry<String, Integer> entry : selectedProducts.entrySet()) {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();

                    Product product = productService.findProductByName(productName);
                    if (product == null) {
                        throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + productName);
                    }
                    int availableStock = product.getStock();
                    if (quantity > availableStock) {
                        throw new IllegalArgumentException("[ERROR] 재고가 부족합니다: " + productName);
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
        Map<Product, Integer> originalQuantities = cartService.getOriginalQuantities();
        Map<Product, Integer> updatedCartItems = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : originalQuantities.entrySet()) {
            Product product = entry.getKey();
            int originalQuantity = entry.getValue();

            PromotionResult promoResult = promotionService.applyPromotion(product, originalQuantity);

            if (promoResult.isPromotionApplied()) {
                int finalQuantity = promoResult.getPromoAvailableQuantity() + promoResult.getFreeQuantity();
                updatedCartItems.put(product, finalQuantity);
            } else {
                updatedCartItems.put(product, originalQuantity);
            }
        }

        cartService.setItems(updatedCartItems);
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
            Map<Product, Integer> cartItems = cartService.getItems();
            Receipt receipt = checkoutService.checkout(cartItems, applyMembership);
            outputView.displayReceipt(receipt);

            // 장바구니 초기화
            cartService.clearCart();
        } catch (Exception e) {
            outputView.displayError("결제 처리 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

    private String getValidatedInput() {
        String input = inputView.readItem();
        InputValidator.validateFormat(input);
        return input;
    }

    private boolean promptContinueShopping() {
        return inputView.promptContinueShopping();
    }
}
