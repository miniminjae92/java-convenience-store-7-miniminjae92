package store.validator;

import store.common.ErrorMessage;

public class InputValidator {
    private static final String PRODUCT_FORMAT_REGEX = "\\[[가-힣a-zA-Z]+-\\d+](,\\[[가-힣a-zA-Z]+-\\d+])*";

    public static void validateFormat(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage());
        }

        if (!input.matches(PRODUCT_FORMAT_REGEX)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_FORMAT.getMessage());
        }
    }
}
