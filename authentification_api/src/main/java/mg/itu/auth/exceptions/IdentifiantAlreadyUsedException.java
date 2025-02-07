package mg.itu.auth.exceptions;

public class IdentifiantAlreadyUsedException extends RuntimeException {
    public IdentifiantAlreadyUsedException(String message) {
        super(message);
    }
}