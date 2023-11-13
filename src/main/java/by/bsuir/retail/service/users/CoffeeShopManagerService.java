package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.CoffeeShopManagerRepository;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.report.ProfitabilityService;
import by.bsuir.retail.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoffeeShopManagerService {
    private final CoffeeShopManagerRepository coffeeShopManagerRepository;
    private final CoffeeShopService coffeeShopService;
    private final ProfitabilityService profitabilityService;
    public CoffeeShopManager findByUsername(String username) {
        return coffeeShopManagerRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Role.COFFEE_SHOP_MANAGER));
    }

    public CoffeeShopManager saveManager(CoffeeShopManager manager) {
        return coffeeShopManagerRepository.save(manager);
    }

    public void deleteManager(long id) {
        coffeeShopManagerRepository.deleteById(id);
    }

}
