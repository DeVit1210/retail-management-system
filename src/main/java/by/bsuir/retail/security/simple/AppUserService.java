package by.bsuir.retail.security.simple;

import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.repository.users.CoffeeShopManagerRepository;
import by.bsuir.retail.repository.users.NetworkManagerRepository;
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
}
