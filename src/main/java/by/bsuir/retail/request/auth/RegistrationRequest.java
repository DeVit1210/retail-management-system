package by.bsuir.retail.request.auth;

import lombok.Getter;

@Getter
public class RegistrationRequest {
    private String username;
    private String password;
    private String fullName;
    private long coffeeShopId;
    private String role;
}
