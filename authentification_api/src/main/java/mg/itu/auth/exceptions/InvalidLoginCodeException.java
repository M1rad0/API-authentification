package mg.itu.auth.exceptions;

public class InvalidLoginCodeException extends RuntimeException {
    public InvalidLoginCodeException(String message) {
        super(message);
    }
}