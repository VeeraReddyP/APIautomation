package assertions;

public enum ResponseStatusCode {

    SUCCESSFUL(200),
    SUCCESSFUL_CREATED(201),
    SUCCESSFUL_NO_CONTENT(204),
    SUCCESSFUL_ACCEPTED(202),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CLIENT_CONFLICT_ERROR(409),
    GONE(410),
    INTERNAL_SERVER_ERROR(500),
    BACKEND_ERROR(501),
    METHOD_NOT_ALLOWED(405);

    private int statusCode;

    ResponseStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
