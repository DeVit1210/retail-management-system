package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.repository.CoffeeShopRepository;
import by.bsuir.retail.testbuilder.impl.CoffeeShopManagerTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class CoffeeShopManagerRepositoryTest {
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;
    @Autowired
    private CoffeeShopManagerRepository coffeeShopManagerRepository;
    @Value("${test.manager.username}")
    private String managerUsername;
    @Test
    @Rollback
    void testFindByUsernameSuccess() {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        coffeeShopRepository.save(coffeeShop);
        CoffeeShopManager coffeeShopManager = CoffeeShopManagerTestBuilder.builder()
                .withManagedCoffeeShop(coffeeShop)
                .withUsername(managerUsername)
                .build();
        coffeeShopManagerRepository.save(coffeeShopManager);
        Optional<CoffeeShopManager> result = coffeeShopManagerRepository.findByUsername(managerUsername);
        assertTrue(result.isPresent());
        assertEquals(result.get().getUsername(), managerUsername);
    }

}