package by.bsuir.retail.response.buidler;

import by.bsuir.retail.response.auth.AuthenticationResponse;
import by.bsuir.retail.security.jwt.JwtUtils;
import by.bsuir.retail.security.jwt.claims.ClaimsBuilderInvoker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseBuilder {
    private final JwtUtils jwtUtils;
    private final ClaimsBuilderInvoker claimsBuilderInvoker;
    public ResponseEntity<AuthenticationResponse> buildAuthenticationResponse(Authentication authentication) {
        GrantedAuthority authority = authentication.getAuthorities().stream().findFirst().orElseThrow();
        String username = (String) authentication.getPrincipal();
        Map<String, Object> claims = claimsBuilderInvoker.newClaimsBuilder(authority).buildClaims(username);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .authenticationToken(jwtUtils.generateToken(claims, username))
                .build()
        );
    }
}
