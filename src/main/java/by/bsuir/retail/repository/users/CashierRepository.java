package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.users.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CashierRepository extends JpaRepository<Cashier, Long> {
    @Query("SELECT s FROM Cashier s WHERE s.login = :username")
    Optional<Cashier> findByUsername(@Param("username") String username);
}