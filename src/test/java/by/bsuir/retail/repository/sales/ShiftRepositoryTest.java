package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import by.bsuir.retail.testbuilder.impl.ShiftTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class ShiftRepositoryTest {
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private CashierRepository cashierRepository;
    @Value("#{T(java.time.LocalDateTime).parse('${test.shift.endTime}')}")
    private LocalDateTime shiftEndTime;

    private final Cashier firstCashier = CashierTestBuilder.builder().build();

    private final Cashier secondCashier = CashierTestBuilder.builder().build();

    private final Cashier thirdCashier = CashierTestBuilder.builder().build();

    @BeforeEach
    void setUp() {
        cashierRepository.saveAll(List.of(firstCashier, secondCashier, thirdCashier));
    }

    @Test
    @Rollback
    void testFindAllByCashier() {
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(firstCashier).build(),
                ShiftTestBuilder.builder().withCashier(secondCashier).build(),
                ShiftTestBuilder.builder().withCashier(firstCashier).build()
        ));
        List<Shift> withFirstCashier = shiftRepository.findByCashier(firstCashier);
        List<Shift> withSecondCashier = shiftRepository.findByCashier(secondCashier);
        List<Shift> withThirdCashier = shiftRepository.findByCashier(thirdCashier);

        assertEquals(2, withFirstCashier.size());
        assertEquals(1, withSecondCashier.size());
        assertEquals(0, withThirdCashier.size());
    }

    @Test
    @Rollback
    void testFindByCashierAndActiveIsTrueSuccess() {
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(firstCashier).build(),
                ShiftTestBuilder.builder().withCashier(firstCashier).withActive(false).build(),
                ShiftTestBuilder.builder().withCashier(secondCashier).withActive(false).build(),
                ShiftTestBuilder.builder().withCashier(thirdCashier).build()
        ));

        Optional<Shift> withFirstCashier = shiftRepository.findByCashierAndActiveIsTrue(firstCashier);
        Optional<Shift> withSecondCashier = shiftRepository.findByCashierAndActiveIsTrue(secondCashier);
        Optional<Shift> withThirdCashier = shiftRepository.findByCashierAndActiveIsTrue(thirdCashier);

        assertTrue(withFirstCashier.isPresent());
        assertFalse(withSecondCashier.isPresent());
        assertTrue(withThirdCashier.isPresent());
    }

    @Test
    @Rollback
    void testExistsByCashierAndActiveIsTrueSuccess() {
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(firstCashier).withActive(false).build(),
                ShiftTestBuilder.builder().withCashier(firstCashier).build(),
                ShiftTestBuilder.builder().withCashier(firstCashier).withActive(false).build()
        ));

        boolean result = shiftRepository.existsByCashierAndActiveIsTrue(firstCashier);
        assertTrue(result);
    }

    @Test
    @Rollback
    void testExistsByCashierAndActiveIsTrueActiveShiftsNotFound() {
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(firstCashier).withActive(false).build(),
                ShiftTestBuilder.builder().withCashier(firstCashier).withActive(false).build()
        ));

        boolean result = shiftRepository.existsByCashierAndActiveIsTrue(firstCashier);
        assertFalse(result);
    }

    @Test
    @Rollback
    void testExistsByCashierAndActiveIsTrueCashierIsNotFound() {
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(secondCashier).build(),
                ShiftTestBuilder.builder().withCashier(thirdCashier).build()
        ));

        boolean result = shiftRepository.existsByCashierAndActiveIsTrue(firstCashier);
        assertFalse(result);
    }

    @Test
    @Rollback
    void testFindByActiveIsTrue() {
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(firstCashier).build(),
                ShiftTestBuilder.builder().withCashier(secondCashier).withActive(false).build(),
                ShiftTestBuilder.builder().withCashier(thirdCashier).build()
        ));

        List<Shift> result = shiftRepository.findByActiveIsTrue();
        assertEquals(2, result.size());
    }

    @Test
    @Rollback
    void testFindByCashierInSuccess() {
        List<Cashier> cashierList = List.of(firstCashier, secondCashier);
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(firstCashier).build(),
                ShiftTestBuilder.builder().withCashier(secondCashier).build(),
                ShiftTestBuilder.builder().withCashier(thirdCashier).build()
        ));

        List<Shift> result = shiftRepository.findByCashierIn(cashierList);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(shift -> shift.getCashier().equals(firstCashier)));
        assertTrue(result.stream().anyMatch(shift -> shift.getCashier().equals(secondCashier)));
    }

    @Test
    @Rollback
    void testFindByCashierInNotFound() {
        List<Cashier> cashierList = List.of(firstCashier, secondCashier);
        shiftRepository.saveAll(List.of(
                ShiftTestBuilder.builder().withCashier(thirdCashier).build(),
                ShiftTestBuilder.builder().withCashier(thirdCashier).build(),
                ShiftTestBuilder.builder().withCashier(thirdCashier).build()
        ));

        List<Shift> result = shiftRepository.findByCashierIn(cashierList);
        assertTrue(result.isEmpty());
    }
}