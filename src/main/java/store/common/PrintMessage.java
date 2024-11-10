package store.common;

public enum PrintMessage {
    WELCOME_MESSAGE("안녕하세요. W편의점입니다."),
    STOCK_INFO_HEADER("현재 보유하고 있는 상품입니다."),
    PRODUCT_INPUT_PROMPT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    MEMBERSHIP_PROMPT("멤버십을 적용하시겠습니까? (Y/N)"),
    ADDITIONAL_PURCHASE_PROMPT("현재 %s을(를) %d개 더 구매하면 추가 혜택을 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    REGULAR_PRICE_PURCHASE_PROMPT("현재 %s의 %d개는 프로모션 혜택 없이 정가로 결제됩니다. 구매하시겠습니까? (Y/N)");

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
