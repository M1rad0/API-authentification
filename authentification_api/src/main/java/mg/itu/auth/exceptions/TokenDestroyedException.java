package mg.itu.auth.exceptions;

public class TokenDestroyedException extends RuntimeException {
    public TokenDestroyedException(String message) {
        super(message);
    }
}