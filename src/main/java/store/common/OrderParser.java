package store.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderParser {
    public static Map<String, Integer> parseProductInput(String input) {
        Map<String, Integer> productMap = new LinkedHashMap<>();

        Pattern pattern = Pattern.compile("\\[(.+?)-(\\d+)]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String productName = matcher.group(1);
            int quantity = Integer.parseInt(matcher.group(2));
            productMap.put(productName, quantity);
        }

        return productMap;
    }
}
