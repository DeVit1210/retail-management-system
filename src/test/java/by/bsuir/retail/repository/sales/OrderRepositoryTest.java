package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import by.bsuir.retail.testbuilder.impl.OrderTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CashierRepository cashierRepository;

    private final Cashier cashier = CashierTestBuilder.builder().build();
    private final Cashier wrongCashier = CashierTestBuilder.builder().build();

    @BeforeEach
    void setUp() {
        cashierRepository.saveAll(List.of(cashier, wrongCashier));
    }

    @Test
    @Rollback
    void testFindAllByCashierAndBetweenSuccess() {
        orderRepository.saveAll(List.of(
                OrderTestBuilder.builder().withCashier(cashier).withCreatedAt(LocalDateTime.now().minusDays(10)).build(),
                OrderTestBuilder.builder().withCashier(wrongCashier).withCreatedAt(LocalDateTime.now().minusDays(2)).build(),
                OrderTestBuilder.builder().withCashier(cashier).withCreatedAt(LocalDateTime.now().minusDays(1)).build()
        ));
        List<Order> result = orderRepository.findByCashierAndCreatedAtBetween(
                cashier, LocalDateTime.now().minusDays(5), LocalDateTime.now()
        );
        assertEquals(1, result.size());
    }

    @Test
    @Rollback
    void testFindAllByCashierAndBetweenNotFoundWithCashier() {
        orderRepository.saveAll(List.of(
                OrderTestBuilder.builder().withCashier(wrongCashier).withCreatedAt(LocalDateTime.now().minusDays(1)).build(),
                OrderTestBuilder.builder().withCashier(wrongCashier).withCreatedAt(LocalDateTime.now().minusDays(1)).build(),
                OrderTestBuilder.builder().withCashier(wrongCashier).withCreatedAt(LocalDateTime.now().minusDays(1)).build()
        ));
        List<Order> result = orderRepository.findByCashierAndCreatedAtBetween(
                cashier, LocalDateTime.now().minusDays(5), LocalDateTime.now()
        );
        assertTrue(result.isEmpty());
    }

    @Test
    @Rollback
    void testFindAllByCashierAndBetweenNotFoundBetween() {
        orderRepository.saveAll(List.of(
                OrderTestBuilder.builder().withCashier(cashier).withCreatedAt(LocalDateTime.now().minusDays(10)).build(),
                OrderTestBuilder.builder().withCashier(cashier).withCreatedAt(LocalDateTime.now().minusDays(10)).build(),
                OrderTestBuilder.builder().withCashier(cashier).withCreatedAt(LocalDateTime.now().minusDays(10)).build()
        ));
        List<Order> result = orderRepository.findByCashierAndCreatedAtBetween(
                cashier, LocalDateTime.now().minusDays(5), LocalDateTime.now()
        );
        assertTrue(result.isEmpty());
    }
}