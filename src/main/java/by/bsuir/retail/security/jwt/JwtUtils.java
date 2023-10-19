package by.bsuir.retail.security.jwt;

import by.bsuir.retail.security.exception.AuthException;
import by.bsuir.retail.security.exception.TokenExpiredException;
import by.bsuir.retail.security.VerificationResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expirationInMillis;
    public String getTokenHeader() {
        return "Bearer ";
    }
    public String getAuthHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMillis))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

//    public boolean isTokenValid(String token, UserDetails userDetails) throws TokenExpiredException {
//        final String username = extractUsername(token);
//        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));
//    }

    public Mono<VerificationResult> isTokenExpired(String token)  {
        if(extractExpirationDate(token).after(new Date(System.currentTimeMillis()))) {
            throw new AuthException("token is expired!", "TOKEN_EXPIRED");
        }
        return Mono.just(new VerificationResult(extractAllClaims(token), token));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
