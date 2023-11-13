package by.bsuir.retail.service.sales;

import by.bsuir.retail.dto.sales.OrderDto;
import by.bsuir.retail.dto.sales.ShiftDto;
import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.ShiftMapper;
import by.bsuir.retail.repository.sales.ShiftRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.exception.ShiftAlreadyOpenedException;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import by.bsuir.retail.testbuilder.impl.CoffeeShopTestBuilder;
import by.bsuir.retail.testbuilder.impl.ShiftTestBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    @Value("${test.shift.totalIncome}")
    private double shiftTotalIncome;
    @Value("${test.shift.quantity}")
    private int shiftQuantity;
    @Value("${test.coffeeShop.id}")
    private long coffeeShopId;
    @Value("${test.cashier.quantity}")
    private int cashierQuantity;

    @Test
    void findActiveByCashierSuccess() {
        Cashier cashier = mock(Cashier.class);
        Shift shift = mock(Shift.class);
        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(Optional.of(shift));
        assertDoesNotThrow(() -> shiftService.findActiveByCashier(cashier));
        verify(shiftRepository, times(1)).findByCashierAndActiveIsTrue(cashier);
    }

    @Test
    void findActiveByCashierNotFound() {
        Cashier cashier = mock(Cashier.class);
        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(Optional.empty());
        assertThrowsExactly(IllegalStateException.class, () -> shiftService.findActiveByCashier(cashier));
    }

    @Test
    void getCurrentShiftHistory() {
        Cashier cashier = mock(Cashier.class);
        Shift shift = mock(Shift.class);
        List<OrderDto> shiftHistory = Collections.nCopies(5, mock(OrderDto.class));
        var responseEntity = ResponseEntity.ok(MultipleEntityResponse.builder().response(shiftHistory).build());

        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(Optional.of(shift));
        when(orderService.getShiftOrderList(any(Shift.class))).thenReturn(responseEntity);

        var result = shiftService.getCurrentShiftHistory(cashier);
        verify(shiftRepository, times(1)).findByCashierAndActiveIsTrue(cashier);
        verify(orderService, times(1)).getShiftOrderList(shift);
        assertEquals(shiftHistory, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void getCoffeeShopActiveShifts() {
        List<Cashier> cashierList = Collections.nCopies(cashierQuantity, CashierTestBuilder.builder().withEnabled(true).build());
        CoffeeShop coffeeShop = CoffeeShopTestBuilder.builder().withCashierList(cashierList).build();
        List<Shift> shiftList = Collections.nCopies(shiftQuantity, mock(Shift.class));
        List<ShiftDto> shiftDtoList = Collections.nCopies(shiftQuantity, mock(ShiftDto.class));

        when(coffeeShopService.findById(anyLong())).thenReturn(coffeeShop);
        when(shiftRepository.findByCashierInAndActiveIsTrue(anyList())).thenReturn(shiftList);
        when(orderService.getShiftTotalIncome(any(Shift.class))).thenReturn(shiftTotalIncome);
        when(shiftMapper.toShiftDtoList(anyList(), anyList())).thenReturn(shiftDtoList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();

        var result = shiftService.getCoffeeShopActiveShifts(coffeeShopId);
        verify(coffeeShopService, times(1)).findById(coffeeShopId);
        verify(shiftRepository, times(1)).findByCashierInAndActiveIsTrue(cashierList);
        verify(orderService, times(shiftQuantity)).getShiftTotalIncome(any(Shift.class));
        verify(shiftMapper, times(1)).toShiftDtoList(anyList(), anyList());
        verify(responseBuilder, times(1)).buildMultipleEntityResponse(shiftDtoList);
        assertEquals(shiftDtoList, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void getShiftHistory() {
        Cashier cashier = mock(Cashier.class);
        List<Shift> shiftList = Collections.nCopies(shiftQuantity, mock(Shift.class));
        List<ShiftDto> shiftDtoList = Collections.nCopies(shiftQuantity, mock(ShiftDto.class));

        when(shiftRepository.findByCashier(any(Cashier.class))).thenReturn(shiftList);
        when(orderService.getShiftTotalIncome(any(Shift.class))).thenReturn(shiftTotalIncome);
        when(shiftMapper.toShiftDtoList(anyList(), anyList())).thenReturn(shiftDtoList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();

        var result = shiftService.getShiftHistory(cashier);
        verify(shiftRepository, times(1)).findByCashier(cashier);
        verify(orderService, times(shiftQuantity)).getShiftTotalIncome(any(Shift.class));
        assertEquals(shiftDtoList, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void openShiftSuccess() {
        Shift shift = mock(Shift.class);
        Cashier cashier = mock(Cashier.class);

        when(shiftRepository.existsByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(false);
        when(shiftRepository.save(any(Shift.class))).thenReturn(shift);

        assertDoesNotThrow(() -> shiftService.openShift(cashier));
        verify(shiftRepository, times(1)).existsByCashierAndActiveIsTrue(cashier);
        verify(shiftRepository, times(1)).save(any(Shift.class));
    }

    @Test
    void openShiftButAlreadyOpened() {
        Cashier cashier = mock(Cashier.class);
        when(shiftRepository.existsByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(true);
        assertThrowsExactly(ShiftAlreadyOpenedException.class, () -> shiftService.openShift(cashier));
    }

    @Test
    void closeShiftSuccess() {
        Cashier cashier = mock(Cashier.class);
        Shift shift = ShiftTestBuilder.builder().withActive(true).build();
        ShiftDto shiftDto = mock(ShiftDto.class);

        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenReturn(Optional.of(shift));
        when(orderService.getShiftTotalIncome(any(Shift.class))).thenReturn(shiftTotalIncome);
        when(shiftRepository.save(any(Shift.class))).thenReturn(shift);
        when(shiftMapper.toShiftDto(any(Shift.class), anyDouble())).thenReturn(shiftDto);
        when(responseBuilder.buildSingleEntityResponse(any(RetailManagementEntity.class))).thenCallRealMethod();

        var result = shiftService.closeShift(cashier);
        assertEquals(shiftDto, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void closeShiftButAlreadyClosed() {
        Cashier cashier = mock(Cashier.class);
        when(shiftRepository.findByCashierAndActiveIsTrue(any(Cashier.class))).thenThrow(IllegalStateException.class);
        assertThrowsExactly(IllegalStateException.class, () -> shiftService.closeShift(cashier));
    }

    @Test
    void testGetCurrentShiftsStatistics() {
        List<Shift> shiftList = Collections.nCopies(shiftQuantity, mock(Shift.class));
        List<ShiftDto> shiftDtoList = Collections.nCopies(shiftQuantity, mock(ShiftDto.class));

        when(shiftRepository.findByActiveIsTrue()).thenReturn(shiftList);
        when(orderService.getShiftTotalIncome(any(Shift.class))).thenReturn(shiftTotalIncome);
        when(shiftMapper.toShiftDtoList(anyList(), anyList())).thenReturn(shiftDtoList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();

        var result = shiftService.getAllActiveShiftsStatistics();
        assertEquals(shiftDtoList, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void testGetCoffeeShopShifts() {
        List<Cashier> cashierList = Collections.nCopies(cashierQuantity, CashierTestBuilder.builder().withEnabled(true).build());
        CoffeeShop coffeeShop = CoffeeShopTestBuilder.builder().withCashierList(cashierList).build();
        List<Shift> shiftList = Collections.nCopies(shiftQuantity, mock(Shift.class));
        List<ShiftDto> shiftDtoList = Collections.nCopies(shiftQuantity, mock(ShiftDto.class));

        when(coffeeShopService.findById(anyLong())).thenReturn(coffeeShop);
        when(shiftRepository.findByCashierIn(anyList())).thenReturn(shiftList);
        when(orderService.getShiftTotalIncome(any(Shift.class))).thenReturn(shiftTotalIncome);
        when(shiftMapper.toShiftDtoList(anyList(), anyList())).thenReturn(shiftDtoList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();

        var result = shiftService.getCoffeeShopShiftHistory(coffeeShopId);
        assertEquals(shiftDtoList, Objects.requireNonNull(result.getBody()).getResponse());
    }
}