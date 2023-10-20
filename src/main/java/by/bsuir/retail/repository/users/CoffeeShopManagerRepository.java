package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.users.CoffeeShopManager;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface CoffeeShopManagerRepository extends ReactiveCrudRepository<CoffeeShopManager, Long> {
    Optional<CoffeeShopManager> findByUsername(String username);
}
