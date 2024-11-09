package store.view;

import store.common.PrintMessage;
import store.dto.ProductDTO;
import java.util.List;

public class OutputView {

    public void displayWelcomeMessage() {
        System.out.println(PrintMessage.WELCOME_MESSAGE.getMessage());
    }

    public void displayStockInfoHeader() {
        System.out.println(PrintMessage.STOCK_INFO_HEADER.getMessage());
    }

    public void displayProducts(List<ProductDTO> products) {
        for (ProductDTO product : products) {
            System.out.println(PrintMessage.PRODUCT_INFO_FORMAT.format(
                    product.getName(), product.getPrice(), product.getStockInfo(), product.getPromotionInfo()
            ));
        }
    }
}
