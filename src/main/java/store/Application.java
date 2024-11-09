package store;

import store.config.DataLoader;
import store.controller.StoreController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.ProductService;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        PromotionRepository promotionRepository = new PromotionRepository();

        DataLoader dataLoader = new DataLoader(productRepository, promotionRepository);

        String productFilePath = "src/main/resources/products.md";
        String promotionFilePath = "src/main/resources/promotions.md";

        dataLoader.initializeData(productFilePath, promotionFilePath);

        ProductService productService = new ProductService(productRepository);
        OutputView outputView = new OutputView();
        StoreController storeController = new StoreController(productService, outputView);

        storeController.displayAvailableProducts();
    }
}
