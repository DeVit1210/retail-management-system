package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.testbuilder.impl.OrderLineTestBuilder;
import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class OrderLineRepositoryTest {
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Test
    @Rollback
    void testFindAllBySoldAfterSuccess() {
        orderLineRepository.saveAll(List.of(
                OrderLineTestBuilder.builder().withSoldAt(LocalDateTime.now().plusDays(1)).build(),
                OrderLineTestBuilder.builder().withSoldAt(LocalDateTime.now().plusDays(2)).build(),
                OrderLineTestBuilder.builder().withSoldAt(LocalDateTime.now().minusDays(1)).build()
        ));
        List<OrderLine> result = orderLineRepository.findAllBySoldAtAfter(LocalDateTime.now());
        assertEquals(2, result.size());
    }

    @Test
    @Rollback
    void testFindAllBySoldAfterNotFound() {
        orderLineRepository.saveAll(List.of(
                OrderLineTestBuilder.builder().withSoldAt(LocalDateTime.now().minusDays(1)).build(),
                OrderLineTestBuilder.builder().withSoldAt(LocalDateTime.now().minusDays(2)).build(),
                OrderLineTestBuilder.builder().withSoldAt(LocalDateTime.now().minusDays(3)).build()
        ));
        List<OrderLine> result = orderLineRepository.findAllBySoldAtAfter(LocalDateTime.now());
        assertTrue(result.isEmpty());
    }
}