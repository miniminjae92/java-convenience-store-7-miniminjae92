package store.common;

public enum FormatMessage {
    PRODUCT_INFO("- %s %s원 %s %s");

    private final String template;

    FormatMessage(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return AmountFormatUtil.format(template, args);
    }
}
