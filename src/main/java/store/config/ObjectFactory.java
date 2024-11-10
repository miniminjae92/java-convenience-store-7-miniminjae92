package store.config;

import store.controller.StoreController;
import store.domain.Cart;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.CartService;
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class ObjectFactory {

    public static StoreController createStoreController() {
        ProductRepository productRepository = new ProductRepository();
        PromotionRepository promotionRepository = new PromotionRepository();

        DataLoader dataLoader = new DataLoader(productRepository, promotionRepository);
        dataLoader.initializeData("src/main/resources/products.md", "src/main/resources/promotions.md");

        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(new Cart(), productRepository);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        return new StoreController(productService, cartService, inputView, outputView);
    }
}
