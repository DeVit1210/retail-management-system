package by.bsuir.retail.service.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException() {
        super("email has already been taken by another user!");
    }
}
