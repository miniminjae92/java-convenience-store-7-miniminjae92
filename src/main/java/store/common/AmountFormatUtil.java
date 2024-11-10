package store.common;

import java.text.NumberFormat;

public class AmountFormatUtil {

    private AmountFormatUtil() {
    }

    public static String format(String template, Object... args) {
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
