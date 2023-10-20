package by.bsuir.retail.security.manager;

import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.security.exception.AuthException;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractRetailAuthenticationManager implements AuthenticationManager {
    private static final String ROLE_PREFIX = "ROLE_";
    protected Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role.name()));
    }

    protected void verifyUser(UserDetails userDetails, Class<?> clazz) {
        if(!userDetails.isAccountNonLocked()) {
            throw new AuthException(clazz.getSimpleName() + " is blocked for some reason", "ACCOUNT_LOCKED");
        } else if(!userDetails.isEnabled()) {
            throw new AuthException(clazz.getSimpleName() + " is not enabled yet!", "ACCOUNT_NOT_ENABLED");
        }
    }
}
