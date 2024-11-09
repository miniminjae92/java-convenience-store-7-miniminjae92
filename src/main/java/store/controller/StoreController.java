package store.controller;

import store.service.ProductService;
import store.view.OutputView;

public class StoreController {
    private final ProductService productService;
    private final OutputView outputView;

    public StoreController(ProductService productService, OutputView outputView) {
        this.productService = productService;
        this.outputView = outputView;
    }

    public void displayAvailableProducts() {
        outputView.displayWelcomeMessage();
        outputView.displayStockInfoHeader();
        outputView.displayProducts(productService.getAllProducts());
    }
}
