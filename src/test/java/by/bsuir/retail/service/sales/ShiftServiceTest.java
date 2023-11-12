package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.ShiftMapper;
import by.bsuir.retail.repository.sales.ShiftRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.service.CoffeeShopService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ShiftServiceTest {
    @InjectMocks
    private ShiftService shiftService;
    @Mock
    private ShiftRepository shiftRepository;
    @Mock
    private ResponseBuilder responseBuilder;
    @Mock
    private OrderService orderService;
    @Mock
    private CoffeeShopService coffeeShopService;
    @Mock
    private ShiftMapper shiftMapper;

    @Test
    void findActiveByCashierSuccess() {
        Cashier cashier = mock(Cashier.class);
        Shift shift = mock(Shift.class);
        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(Optional.of(shift));
        assertDoesNotThrow(() -> shiftService.findActiveByCashier(cashier));
        verify(shiftRepository, times(2)).existsByCashierAndActiveIsTrue(cashier);
    }

    @Test
    void findActiveByCashierNotFound() {
        Cashier cashier = mock(Cashier.class);
        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(Optional.empty());
        assertThrowsExactly(IllegalStateException.class, () -> shiftService.findActiveByCashier(cashier));
    }

    @Test
    void getCurrentShiftHistory() {
    }

    @Test
    void getCoffeeShopActiveShifts() {
    }

    @Test
    void getShiftHistory() {
    }

    @Test
    void openShift() {
    }

    @Test
    void closeShift() {
    }

    @Test
    void getCurrentShiftsStatistics() {
    }

    @Test
    void getCoffeeShopShiftHistory() {
    }
}