package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.users.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashierRepository extends JpaRepository<Cashier, Long> {
    Optional<Cashier> findByUsername(String username);
}