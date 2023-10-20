package by.bsuir.retail.security.enhanced.manager;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.security.enhanced.converter.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashierAuthenticationManager extends AbstractRetailAuthenticationManager {
    private final CashierRepository cashierRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        // TODO: create some custom exception for wrong user principals
        Cashier cashier = cashierRepository.findByUsername(principal.getUsername())
                .orElseThrow(RuntimeException::new);
        verifyUser(cashier, Cashier.class);
        return new UsernamePasswordAuthenticationToken(principal, null, getAuthorities(Role.CASHIER));
    }
}
