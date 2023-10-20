package by.bsuir.retail.security;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerificationResult {
    private Claims claims;
    private String token;
}
