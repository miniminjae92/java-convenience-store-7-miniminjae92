package store;

import store.config.ObjectFactory;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = ObjectFactory.createStoreController();
        storeController.displayAvailableProducts();
        storeController.processUserSelection();
    }
}
