package by.bsuir.retail.service.exception;

import lombok.AllArgsConstructor;

public class WrongRetailEntityIdException extends RuntimeException {
    public WrongRetailEntityIdException(Class<?> clazz, long id) {
        super(clazz.getSimpleName() + " with such id was not found: " + id);
    }
}
