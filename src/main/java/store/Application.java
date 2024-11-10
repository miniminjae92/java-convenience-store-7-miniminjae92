package store;

import store.config.ObjectFactory;
import store.controller.StoreController;
// [콜라-3],[에너지바-5]
public class Application {
    public static void main(String[] args) {
        StoreController storeController = ObjectFactory.createStoreController();
        storeController.start();
    }
}
