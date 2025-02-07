package mg.itu.auth.exceptions.responses;

public class ApiErrorResponse {
    private int httpCode;     // Standard HTTP status
    private int errorCode;    // Custom error code
    private String message;   // Custom error message

    public ApiErrorResponse(int httpCode, int errorCode, String message) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getHttpCode() { return httpCode; }
    public int getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
}