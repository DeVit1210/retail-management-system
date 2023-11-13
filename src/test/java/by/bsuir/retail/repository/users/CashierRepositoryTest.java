package by.bsuir.retail.repository.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.repository.CoffeeShopRepository;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class CashierRepositoryTest {
    @Autowired
    private CashierRepository cashierRepository;
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;
    @Value("${test.cashier.username}")
    private String cashierUsername;
    @Test
    @Rollback
    void testFindByUsernameSuccess() {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        coffeeShopRepository.save(coffeeShop);
        Cashier cashier = CashierTestBuilder.builder().withUsername(cashierUsername).withCoffeeShop(coffeeShop).build();
        cashierRepository.save(cashier);
        Optional<Cashier> cashierOptional = cashierRepository.findByUsername(cashierUsername);
        assertTrue(cashierOptional.isPresent());
    }
}