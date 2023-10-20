package by.bsuir.retail.security.converter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerificationResult {
    private Claims claims;
    private String token;
}
