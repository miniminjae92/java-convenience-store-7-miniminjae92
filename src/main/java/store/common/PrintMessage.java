package store.common;

public enum PrintMessage {
    PRODUCT_INFO_FORMAT("- %s %d원 %s %s"),
    WELCOME_MESSAGE("안녕하세요. W편의점입니다."),
    STOCK_INFO_HEADER("현재 보유하고 있는 상품입니다.");

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
