package by.bsuir.retail.service.exception;


public class ShiftAlreadyOpenedException extends RuntimeException {
    public ShiftAlreadyOpenedException(long cashierId) {
        super("shift is already opened for a cashier with such id: " + cashierId);
    }
}
