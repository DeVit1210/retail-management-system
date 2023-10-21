package by.bsuir.retail.security.jwt.claims;

import by.bsuir.retail.entity.users.Role;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ClaimsBuilder {
    Map<String, Object> buildClaims(String username);
}
