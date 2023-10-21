package by.bsuir.retail.security.simple;


import org.mapstruct.BeanMapping;
import org.mapstruct.Named;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    @Default
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
