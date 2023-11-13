package by.bsuir.retail.service.exception;

import by.bsuir.retail.entity.users.Role;

public class UserNotFoundException extends RuntimeException {
    private Role role;
    public UserNotFoundException(Role role) {
        super(role.name().toLowerCase() + " is not found!");
    }
}
