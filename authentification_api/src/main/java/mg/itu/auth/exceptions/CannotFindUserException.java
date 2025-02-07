package mg.itu.auth.exceptions;

public class CannotFindUserException extends RuntimeException {
    public CannotFindUserException(String message) {
        super(message);
    }
}