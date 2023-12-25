package main.services.myExceptions;

public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
