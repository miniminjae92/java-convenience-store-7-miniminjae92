package store.config;

import store.controller.StoreController;
import store.domain.Membership;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.CartService;
import store.service.CheckoutService;
import store.service.MembershipService;
import store.service.ProductService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class ObjectFactory {

    public static StoreController createStoreController() {
        ProductRepository productRepository = new ProductRepository();
        PromotionRepository promotionRepository = new PromotionRepository();

        DataLoader dataLoader = new DataLoader(productRepository, promotionRepository);
        dataLoader.initializeData("src/main/resources/products.md", "src/main/resources/promotions.md");

        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(productService);
        PromotionService promotionService = new PromotionService(promotionRepository);
        MembershipService membershipService = new MembershipService(new Membership());
        CheckoutService checkoutService = new CheckoutService(promotionService, membershipService);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        return new StoreController(productService, cartService, checkoutService, promotionService, inputView,
                outputView);
    }
}
