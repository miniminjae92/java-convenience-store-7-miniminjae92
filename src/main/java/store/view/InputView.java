package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.ErrorMessage;
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
        System.out.println(PrintMessage.ADD_PURCHASE_PROMPT.getMessage());
        return getUserConfirmation();
    }

    private boolean getUserConfirmation() {
        while (true) {
            String response = Console.readLine();
            if ("Y".equals(response)) {
                System.out.println();
                return true;
            }
            if ("N".equals(response)) {
                System.out.println();
                return false;
            }
            System.out.println(ErrorMessage.GENERIC_INVALID_INPUT.getMessage());
        }
    }
}
