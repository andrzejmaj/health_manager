package engineer.thesis.core.exception;

public class ForbiddenContentException extends RuntimeException {
    public ForbiddenContentException() {
        super("You are not allowed to access this content");
    }
}
