package store.view;

import java.util.Map;
import store.common.FormatMessage;
import store.common.PrintMessage;
import store.domain.Product;
import store.domain.Receipt;
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
            System.out.println(FormatMessage.PRODUCT_INFO.format(
                    product.getName(), product.getPrice(), product.getStockInfo(), product.getPromotionInfo()
            ));
        }
    }

    public void promptAdditionalPurchase(String productName, int additionalQuantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N): ", productName, additionalQuantity);
    }

    public void promptRegularPricePurchase(String productName, int nonPromoQuantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N): ", productName, nonPromoQuantity);
    }

    public void displayReceipt(Receipt receipt) {
        System.out.println(receipt.generateReceipt());
    }

    public void displayError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
