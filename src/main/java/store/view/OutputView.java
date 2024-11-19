package store.view;

import store.common.FormatMessage;
import store.common.PrintMessage;
import store.domain.Receipt;
import store.dto.ProductDTO;
import java.util.List;

public class OutputView {

    public void displayWelcomeMessage() {
        System.out.println(PrintMessage.WELCOME_MESSAGE.getMessage());
    }

    public void displayStockInfoHeader() {
        System.out.println(PrintMessage.STOCK_INFO_HEADER.getMessage());
        System.out.println();
    }

    public void displayProducts(List<ProductDTO> products) {
        for (ProductDTO product : products) {
            System.out.println(FormatMessage.PRODUCT_INFO.format(
                    product.getName(), product.getPrice(), product.getStockInfo(), product.getPromotionInfo()
            ));
        }
        System.out.println();
    }

    public void displayReceipt(Receipt receipt) {
        System.out.println(receipt.generateReceipt());
    }

    public void displayError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
