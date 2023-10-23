package by.bsuir.retail.security.simple;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.mapper.RegistrationRequestMapper;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.repository.users.CoffeeShopManagerRepository;
import by.bsuir.retail.repository.users.NetworkManagerRepository;
import by.bsuir.retail.request.auth.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private final CashierRepository cashierRepository;
    private final NetworkManagerRepository networkManagerRepository;
    private final CoffeeShopManagerRepository coffeeShopManagerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Optional<? extends UserDetails>> userDetailsList = new ArrayList<>();
        userDetailsList.add(cashierRepository.findByUsername(username));
        userDetailsList.add(networkManagerRepository.findByUsername(username));
        userDetailsList.add(coffeeShopManagerRepository.findByUsername(username));
        return userDetailsList.stream()
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .toList()
                .get(0);
    }

    public boolean isUserExists(RegistrationRequest request) {
        final String username = request.getUsername();
        List<Optional<? extends UserDetails>> userDetailsList = new ArrayList<>();
        userDetailsList.add(cashierRepository.findByUsername(username));
        userDetailsList.add(networkManagerRepository.findByUsername(username));
        userDetailsList.add(coffeeShopManagerRepository.findByUsername(username));
        return userDetailsList.stream().anyMatch(Optional::isPresent);
    }

    public UserDetails saveUser(RegistrationRequest request, RegistrationRequestMapper mapper) {
        Role role = Role.extractFromRequest(request);
        if (role.equals(Role.CASHIER)) {
            return cashierRepository.save(mapper.mapToCashier(request));
        } else if(role.equals(Role.COFFEE_SHOP_MANAGER)) {
            return coffeeShopManagerRepository.save(mapper.mapToCoffeeShopManager(request));
        } else throw new IllegalArgumentException("unexpected role: " + role.name());
    }
}
