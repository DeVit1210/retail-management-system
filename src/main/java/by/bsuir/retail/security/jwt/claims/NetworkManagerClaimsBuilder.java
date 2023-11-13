package by.bsuir.retail.security.jwt.claims;

import by.bsuir.retail.entity.users.NetworkManager;
import by.bsuir.retail.service.users.NetworkManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NetworkManagerClaimsBuilder implements ClaimsBuilder {
    private final NetworkManagerService networkManagerService;
    @Override
    public Map<String, Object> buildClaims(String username) {
        Map<String, Object> claims = new HashMap<>();
        NetworkManager networkManager = networkManagerService.findByUsername(username);
        claims.put("password", networkManager.getPassword());
        claims.put("fullName", networkManager.getFullName());
        return claims;
    }
}
