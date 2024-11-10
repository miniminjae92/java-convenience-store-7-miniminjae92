package store.common;

import java.text.NumberFormat;

public enum FormatMessage {
    PRODUCT_INFO("- %s %s원 %s %s");

    private final String template;

    FormatMessage(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        Object[] formattedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Integer) {
                formattedArgs[i] = NumberFormat.getInstance().format(args[i]);
                continue;
            }
            formattedArgs[i] = args[i];
        }
        return String.format(template, formattedArgs);
    }
}
