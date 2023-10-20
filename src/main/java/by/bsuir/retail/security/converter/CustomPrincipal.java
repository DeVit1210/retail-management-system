package by.bsuir.retail.security.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Date;

@Getter
@AllArgsConstructor
public class CustomPrincipal implements Principal {
    private String username;
    private Date expirationDate;
    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
