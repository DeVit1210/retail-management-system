package by.bsuir.retail.service.sales;

import by.bsuir.retail.dto.sales.OrderDto;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.OrderMapper;
import by.bsuir.retail.repository.sales.OrderRepository;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.products.KitchenService;
import by.bsuir.retail.testbuilder.impl.OrderTestBuilder;
import by.bsuir.retail.testbuilder.impl.PaymentTestBuilder;
import by.bsuir.retail.testbuilder.impl.ShiftTestBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private KitchenService kitchenService;
    @Mock
    private OrderLineService orderLineService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private OrderMapper mapper;
    @Mock
    private ResponseBuilder responseBuilder;
    @Value("${test.order.quantity}")
    private int ordersQuantity;
    @Value("${test.order.id}")
    private long orderId;
    @Value("${test.order.cost}")
    private double orderTotalCost;
    @Value("#{T(java.time.LocalDateTime).parse('${test.shift.startTime}')}")
    private LocalDateTime shiftStartTime;
    @Value("#{T(java.time.LocalDateTime).parse('${test.shift.endTime}')}")
    private LocalDateTime shiftEndTime;
    @Test
    void testFindOrderByIdSuccess() {
        Order order = mock(Order.class);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        assertDoesNotThrow(() -> orderService.findById(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testFindByOrderByIdNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrowsExactly(WrongRetailEntityIdException.class, () -> orderService.findById(orderId));
    }

    @Test
    void testGetAll() {
        List<Order> orderList = OrderTestBuilder.builder().times(ordersQuantity);
        List<OrderDto> orderDtoList = Collections.nCopies(ordersQuantity, OrderDto.builder().build());

        when(orderRepository.findAll()).thenReturn(orderList);
        when(mapper.toOrderDtoList(anyList())).thenReturn(orderDtoList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();
        ResponseEntity<MultipleEntityResponse> result = orderService.getAll();

        verify(orderRepository, times(1)).findAll();
        verify(mapper, times(1)).toOrderDtoList(orderList);
        verify(responseBuilder, times(1)).buildMultipleEntityResponse(orderDtoList);
        assertEquals(orderDtoList, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void addOrder() {
        OrderAddingRequest request = mock(OrderAddingRequest.class);
        Order order = mock(Order.class);
        OrderDto orderDto = mock(OrderDto.class);

        when(mapper.toOrder(any(OrderAddingRequest.class))).thenReturn(order);
        when(mapper.toOrderDto(any(Order.class))).thenReturn(orderDto);
        doNothing().when(kitchenService).prepareOrder(any(Order.class));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doNothing().when(orderLineService).createOrderLines(any(Order.class));
        doNothing().when(paymentService).createPayment(any(OrderAddingRequest.class), any(Order.class));
        when(responseBuilder.buildSingleEntityResponse(any(RetailManagementEntity.class))).thenCallRealMethod();

        ResponseEntity<SingleEntityResponse> result = orderService.addOrder(request);
        verify(mapper, times(1)).toOrder(request);
        verify(kitchenService, times(1)).prepareOrder(order);
        verify(orderRepository, times(1)).save(order);
        verify(orderLineService, times(1)).createOrderLines(order);
        verify(paymentService, times(1)).createPayment(request, order);
        verify(mapper, times(1)).toOrderDto(order);
        verify(responseBuilder, times(1)).buildSingleEntityResponse(orderDto);
        assertEquals(orderDto, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void testGetByIdSuccess() {
        Order order = mock(Order.class);
        OrderDto orderDto = mock(OrderDto.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(mapper.toOrderDto(any(Order.class))).thenReturn(orderDto);
        when(responseBuilder.buildSingleEntityResponse(any(RetailManagementEntity.class))).thenCallRealMethod();

        ResponseEntity<SingleEntityResponse> result = orderService.getById(orderId);
        verify(orderRepository, times(1)).findById(orderId);
        verify(mapper, times(1)).toOrderDto(order);
        verify(responseBuilder, times(1)).buildSingleEntityResponse(orderDto);
        assertEquals(orderDto, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void testGetByIdNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrowsExactly(WrongRetailEntityIdException.class, () -> orderService.getById(orderId));
    }

    @Test
    void getShiftTotalIncome() {
        Payment payment = mock(Payment.class);
        Cashier cashier = mock(Cashier.class);
        Shift shift = ShiftTestBuilder.builder()
                .withCashier(cashier).withStartTime(shiftStartTime).withEndTime(shiftEndTime)
                .build();
        List<Order> orderList = Collections.nCopies(ordersQuantity, mock(Order.class));

        orderList.forEach(order -> when(order.getPayment()).thenReturn(payment));
        when(orderRepository.findByCashierAndCreatedAtBetween(any(), any(), any())).thenReturn(orderList);
        when(paymentService.getPaymentTotalCost(any(Payment.class))).thenReturn(orderTotalCost);
        double result = orderService.getShiftTotalIncome(shift);

        verify(orderRepository, times(1))
                .findByCashierAndCreatedAtBetween(cashier, shiftStartTime, shiftEndTime);
        verify(paymentService, times(ordersQuantity)).getPaymentTotalCost(payment);
        assertEquals(result, orderTotalCost * ordersQuantity);
    }

    @Test
    void getShiftOrderList() {
        Cashier cashier = mock(Cashier.class);
        Shift shift = ShiftTestBuilder.builder()
                .withCashier(cashier).withStartTime(shiftStartTime).withEndTime(shiftEndTime)
                .build();
        List<Order> orderList = Collections.nCopies(ordersQuantity, mock(Order.class));
        List<OrderDto> orderDtoList = Collections.nCopies(ordersQuantity, mock(OrderDto.class));

        when(orderRepository.findByCashierAndCreatedAtBetween(any(), any(), any())).thenReturn(orderList);
        when(mapper.toOrderDtoList(anyList())).thenReturn(orderDtoList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();

        ResponseEntity<MultipleEntityResponse> result = orderService.getShiftOrderList(shift);
        verify(orderRepository, times(1))
                .findByCashierAndCreatedAtBetween(cashier, shiftStartTime, shiftEndTime);
        verify(mapper, times(1)).toOrderDtoList(orderList);
        verify(responseBuilder, times(1)).buildMultipleEntityResponse(orderDtoList);
        assertEquals(orderDtoList, Objects.requireNonNull(result.getBody()).getResponse());
    }

    @Test
    void testGetOrderTotalCost() {
        Payment payment = mock(Payment.class);
        Order order = OrderTestBuilder.builder().withPayment(payment).build();
        when(paymentService.getPaymentTotalCost(any(Payment.class))).thenReturn(orderTotalCost);

        double result = orderService.getOrderTotalCost(order);

        verify(paymentService, times(1)).getPaymentTotalCost(payment);
        assertEquals(orderTotalCost, result);
    }



}