package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.NetworkManager;
import by.bsuir.retail.repository.CoffeeShopRepository;
import by.bsuir.retail.testbuilder.impl.NetworkManagerTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class NetworkManagerRepositoryTest {
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;
    @Autowired
    private NetworkManagerRepository networkManagerRepository;
    @Value("${test.manager.username}")
    private String managerUsername;
    @Test
    @Rollback
    void testFindByUsernameSuccess() {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        coffeeShopRepository.save(coffeeShop);
        NetworkManager networkManager = NetworkManagerTestBuilder.builder().withUsername(managerUsername).build();
        networkManagerRepository.save(networkManager);
        Optional<NetworkManager> result = networkManagerRepository.findByUsername(managerUsername);
        assertTrue(result.isPresent());
        assertEquals(result.get().getUsername(), managerUsername);
    }
}