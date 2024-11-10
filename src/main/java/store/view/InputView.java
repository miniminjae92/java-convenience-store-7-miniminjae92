package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.PrintMessage;

public class InputView {
    public String readItem() {
        System.out.println(PrintMessage.PRODUCT_INPUT_PROMPT.getMessage());
        return Console.readLine();
    }
}
