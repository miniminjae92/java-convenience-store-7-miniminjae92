package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.PrintMessage;

public class InputView {

    public String readItem() {
        System.out.println(PrintMessage.PRODUCT_INPUT_PROMPT.getMessage());
        return Console.readLine();
    }

    public boolean promptUserForAdditionalPurchase(String productName, int additionalQuantityNeeded) {
        System.out.print(PrintMessage.ADDITIONAL_PURCHASE_PROMPT.format(productName, additionalQuantityNeeded));
        System.out.println();
        return getUserConfirmation();
    }

    public boolean promptUserForRegularPricePurchase(String productName, int nonPromoQuantity) {
        System.out.print(PrintMessage.REGULAR_PRICE_PURCHASE_PROMPT.format(productName, nonPromoQuantity));
        System.out.println();
        return getUserConfirmation();
    }

    public boolean promptMembership() {
        System.out.println(PrintMessage.MEMBERSHIP_PROMPT.getMessage());
        return getUserConfirmation();
    }

    public boolean promptContinueShopping() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return getUserConfirmation();
    }

    private boolean getUserConfirmation() {
        while (true) {
            String response = Console.readLine();
            if ("Y".equals(response)) {
                return true;
            }
            if ("N".equals(response)) {
                return false;
            }
            System.out.println("[ERROR] 잘못된 입력입니다. 'Y' 또는 'N'을 입력해 주세요.");
        }
    }

    public void displayError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
