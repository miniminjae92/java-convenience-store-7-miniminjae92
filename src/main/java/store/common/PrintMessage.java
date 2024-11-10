package store.common;

public enum PrintMessage {
    WELCOME_MESSAGE("안녕하세요. W편의점입니다."),
    STOCK_INFO_HEADER("현재 보유하고 있는 상품입니다."),
    PRODUCT_INPUT_PROMPT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

    private final String message;

    PrintMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}
