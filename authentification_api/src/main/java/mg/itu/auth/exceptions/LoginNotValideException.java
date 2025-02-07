package mg.itu.auth.exceptions;

public class LoginNotValideException extends RuntimeException {
    public LoginNotValideException(String message) {
        super(message);
    }
}