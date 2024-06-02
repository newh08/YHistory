package YH.YHistory.exception;

public class NotCorrespondingUserIdException extends RuntimeException {
    private final String message;

    public NotCorrespondingUserIdException(String message) {
        this.message = message;
    }
}
