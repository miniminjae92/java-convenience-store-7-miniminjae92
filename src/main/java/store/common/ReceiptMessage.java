package store.common;

public enum ReceiptMessage {
    HEADER("===========W 편의점============="),
    PURCHASE_DETAILS_HEADER("상품명\t\t수량\t금액"),
    FREE_ITEMS_HEADER("===========증    정=============");

    private final String template;

    ReceiptMessage(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return String.format(template, args);
    }
}
