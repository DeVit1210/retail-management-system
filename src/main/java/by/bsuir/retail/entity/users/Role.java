package by.bsuir.retail.entity.users;

import by.bsuir.retail.request.auth.RegistrationRequest;
import org.springframework.security.core.GrantedAuthority;

public enum Role {
    CASHIER, COFFEE_SHOP_MANAGER, NETWORK_MANAGER;
    public static Role fromAuthority(GrantedAuthority grantedAuthority) {
        return Role.valueOf(grantedAuthority.getAuthority());
    }

    public static Role extractFromRequest(RegistrationRequest request) {
        return Role.valueOf(request.getRole());
    }
}
