package store.controller;

import store.common.OrderParser;
import store.service.ProductService;
import store.service.CartService;
import store.validator.InputValidator;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

public class StoreController {
    private final ProductService productService;
    private final CartService cartService;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(ProductService productService, CartService cartService, InputView inputView, OutputView outputView) {
        this.productService = productService;
        this.cartService = cartService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void displayAvailableProducts() {
        outputView.displayWelcomeMessage();
        outputView.displayStockInfoHeader();
        outputView.displayProducts(productService.getAllProducts());
    }

    public void processUserSelection() {
        while (true) {
            try {
                String input = getValidatedInput();
                Map<String, Integer> selectedProducts = OrderParser.parseProductInput(input);
                addProductsToCart(selectedProducts);
                break;

            } catch (IllegalArgumentException e) {
                outputView.displayError(e.getMessage());
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
}
