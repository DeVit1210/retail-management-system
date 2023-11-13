package by.bsuir.retail.security.enhanced.manager;

import by.bsuir.retail.entity.users.NetworkManager;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.NetworkManagerRepository;
import by.bsuir.retail.security.enhanced.converter.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NetworkManagerAuthenticationManager extends AbstractRetailAuthenticationManager {
    private final NetworkManagerRepository networkManagerRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        NetworkManager networkManager = networkManagerRepository.findByUsername(principal.getUsername())
                .orElseThrow(RuntimeException::new);
        verifyUser(networkManager, NetworkManager.class);
        return new UsernamePasswordAuthenticationToken(principal, networkManager, getAuthorities(Role.NETWORK_MANAGER));
    }
}
