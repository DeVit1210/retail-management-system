package by.bsuir.retail.entity.users;

import org.springframework.security.core.GrantedAuthority;

public enum Role {
    CASHIER, COFFEE_SHOP_MANAGER, NETWORK_MANAGER;

    public static Role fromAuthority(GrantedAuthority grantedAuthority) {
        final String rolePrefix = "ROLE_";
        return Role.valueOf(grantedAuthority.getAuthority().substring(rolePrefix.length()));
    }
}
