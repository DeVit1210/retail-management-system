package by.bsuir.retail.security.converter;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class AuthenticationCreator {
    public Authentication create(VerificationResult verificationResult) {
        Claims claims = verificationResult.getClaims();
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();
        CustomPrincipal customPrincipal = new CustomPrincipal(username, expiration);
        return new UsernamePasswordAuthenticationToken(customPrincipal, null);
    }
}
