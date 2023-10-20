package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.NetworkManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface NetworkManagerRepository extends JpaRepository<NetworkManager, Long> {
    @Query("SELECT s FROM NetworkManager s WHERE s.login = :username")
    Optional<NetworkManager> findByUsername(@Param("username") String username);
}
