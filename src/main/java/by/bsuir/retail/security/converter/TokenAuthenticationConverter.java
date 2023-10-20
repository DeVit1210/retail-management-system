package by.bsuir.retail.security.converter;

import by.bsuir.retail.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationConverter implements AuthenticationConverter {
    private static final String BEARER_PREFIX = "Bearer ";
    private final Function<String, String> tokenRetriever = header -> header.substring(BEARER_PREFIX.length());
    private final JwtUtils jwtUtils;
    private final AuthenticationCreator authenticationCreator;

    @Override
    public Authentication convert(HttpServletRequest request) {
        String authenticationHeader = extractHeader(request);
        String token = tokenRetriever.apply(authenticationHeader);
        VerificationResult verificationResult = jwtUtils.verifyToken(token);
        return authenticationCreator.create(verificationResult);
    }

    private String extractHeader(HttpServletRequest request) {
        return request.getHeader(jwtUtils.getAuthHeader());
    }
}
