package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.users.NetworkManager;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.NetworkManagerRepository;
import by.bsuir.retail.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkManagerService {
    private final NetworkManagerRepository networkManagerRepository;
    public NetworkManager findByUsername(String username) {
        return networkManagerRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(Role.NETWORK_MANAGER));
    }
}
