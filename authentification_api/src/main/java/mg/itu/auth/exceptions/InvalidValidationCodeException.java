package mg.itu.auth.exceptions;

public class InvalidValidationCodeException extends RuntimeException {
    public InvalidValidationCodeException(String message) {
        super(message);
    }
}