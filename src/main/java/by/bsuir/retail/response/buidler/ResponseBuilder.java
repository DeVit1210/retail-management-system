package by.bsuir.retail.response.buidler;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.response.auth.AuthenticationResponse;
import by.bsuir.retail.response.auth.RegistrationResponse;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.security.jwt.JwtUtils;
import by.bsuir.retail.security.jwt.claims.ClaimsBuilderInvoker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseBuilder {
    private final JwtUtils jwtUtils;
    private final ClaimsBuilderInvoker claimsBuilderInvoker;
    public ResponseEntity<AuthenticationResponse> buildAuthenticationResponse(Authentication authentication) {
        GrantedAuthority authority = authentication.getAuthorities().stream().findFirst().orElseThrow();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Map<String, Object> claims = claimsBuilderInvoker.newClaimsBuilder(authority).buildClaims(username);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .authenticationToken(jwtUtils.generateToken(claims, username))
                .build()
        );
    }

    public ResponseEntity<RegistrationResponse> buildRegistrationResponse(UserDetails userDetails) {
        return ResponseEntity.ok(RegistrationResponse.builder()
                .registrationMessage("user successfully authenticated with username" +
                        userDetails.getUsername() +
                        "and waiting for the enabling!"
                )
                .build()
        );
    }

    public ResponseEntity<MultipleEntityResponse> buildMultipleEntityResponse(List<? extends RetailManagementEntity> response) {
        return ResponseEntity.ok(MultipleEntityResponse.builder().response(response).build());
    }

    public ResponseEntity<SingleEntityResponse> buildSingleEntityResponse(RetailManagementEntity response) {
        return ResponseEntity.ok(SingleEntityResponse.builder().response(response).build());
    }
}
