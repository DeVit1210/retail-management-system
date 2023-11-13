package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.repository.users.CoffeeShopManagerRepository;
import by.bsuir.retail.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class CoffeeShopManagerServiceTest {
    @InjectMocks
    private CoffeeShopManagerService coffeeShopManagerService;
    @Mock
    private CoffeeShopManagerRepository coffeeShopManagerRepository;
    @Value("${test.manager.username}")
    private String managerUsername;
    @Test
    void testFindByUsernameSuccess() {
        CoffeeShopManager coffeeShopManager = CoffeeShopManager.builder().build();
        when(coffeeShopManagerRepository.findByUsername(anyString())).thenReturn(Optional.of(coffeeShopManager));
        assertDoesNotThrow(() -> coffeeShopManagerService.findByUsername(managerUsername));
        verify(coffeeShopManagerRepository, times(1)).findByUsername(managerUsername);
    }

    @Test
    void testFindByUsernameNotFound() {
        when(coffeeShopManagerRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrowsExactly(UserNotFoundException.class, () -> coffeeShopManagerService.findByUsername(managerUsername));
    }
}