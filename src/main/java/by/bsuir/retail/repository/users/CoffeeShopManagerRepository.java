package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface CoffeeShopManagerRepository extends JpaRepository<CoffeeShopManager, Long> {
    @Query("SELECT s FROM CoffeeShopManager s WHERE s.login = :username")
    Optional<CoffeeShopManager> findByUsername(@Param("username") String username);
}
