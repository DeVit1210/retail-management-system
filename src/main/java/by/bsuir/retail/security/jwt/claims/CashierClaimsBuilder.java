package by.bsuir.retail.security.jwt.claims;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.service.users.CashierService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsMutator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CashierClaimsBuilder implements ClaimsBuilder {
    private final CashierService cashierService;
    @Override
    public Map<String, Object> buildClaims(String username) {
        Cashier cashier = cashierService.findByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("password", cashier.getPassword());
        claims.put("fullName", cashier.getFullName());
        claims.put("coffeeShopId", cashier.getCoffeeShop().getId());
        return claims;
    }
}
