package by.bsuir.retail.security.enhanced.manager;

import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.CoffeeShopManagerRepository;
import by.bsuir.retail.security.enhanced.converter.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoffeeShopManagerAuthenticationManager extends AbstractRetailAuthenticationManager {
    private final CoffeeShopManagerRepository coffeeShopManagerRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        CoffeeShopManager coffeeShopManager = coffeeShopManagerRepository.findByUsername(principal.getUsername())
                .orElseThrow(RuntimeException::new);
        verifyUser(coffeeShopManager, CoffeeShopManager.class);
        return new UsernamePasswordAuthenticationToken(principal, coffeeShopManager, getAuthorities(Role.COFFEE_SHOP_MANAGER));
    }
}
