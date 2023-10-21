package by.bsuir.retail.security.jwt.claims;

import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.service.users.CoffeeShopManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CoffeeShopManagerClaimsBuilder implements ClaimsBuilder {
    private final CoffeeShopManagerService coffeeShopManagerService;
    @Override
    public Map<String, Object> buildClaims(String username) {
        Map<String, Object> claims = new HashMap<>();
        CoffeeShopManager coffeeShopManager = coffeeShopManagerService.findByUsername(username);
        claims.put("password", coffeeShopManager.getPassword());
        claims.put("fullName", coffeeShopManager.getFullName());
        claims.put("managedCoffeeShop", coffeeShopManager.getManagedCoffeeShop());
        return claims;
    }
}
