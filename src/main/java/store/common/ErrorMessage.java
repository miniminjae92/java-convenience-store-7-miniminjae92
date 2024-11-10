package store.common;

public enum ErrorMessage {
    FILE_READ_ERROR("파일을 읽는 중 문제가 발생했습니다."),
    INVALID_DATA_FORMAT("데이터 형식이 잘못되었습니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return ERROR_PREFIX + String.format(message, args);
    }
}
