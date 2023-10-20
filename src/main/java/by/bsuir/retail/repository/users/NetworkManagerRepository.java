package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.users.NetworkManager;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface NetworkManagerRepository extends ReactiveCrudRepository<NetworkManager, Long> {
    Optional<NetworkManager> findByUsername(String username);
}
