package store.common;

import java.util.Arrays;

public class StringUtils {
    public static String[] parseData(String line, int expectedSize) {
        String[] data = Arrays.stream(line.split(Constants.COMMA_DELIMITER))
                .map(String::trim)
                .toArray(String[]::new);

        if (data.length != expectedSize) {
            throw new IllegalArgumentException(ErrorMessage.FILE_READ_ERROR.getMessage());
        }

        return data;
    }
}
