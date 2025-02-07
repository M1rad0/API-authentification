package mg.itu.auth.exceptions;

public class UnverifiedTokenException extends RuntimeException {
    public UnverifiedTokenException(String message) {
        super(message);
    }
}