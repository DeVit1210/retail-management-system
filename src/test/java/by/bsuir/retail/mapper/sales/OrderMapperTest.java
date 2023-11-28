package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderDto;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.service.report.CalculatingService;
import by.bsuir.retail.service.users.CashierService;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import by.bsuir.retail.testbuilder.impl.OrderLineTestBuilder;
import by.bsuir.retail.testbuilder.impl.OrderTestBuilder;
import by.bsuir.retail.testbuilder.impl.ProductTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class OrderMapperTest {
    @InjectMocks
    private OrderMapperImpl mapper;
    @Mock
    private CalculatingService calculatingService;
    @Mock
    private CashierService cashierService;
    @Mock
    private OrderLineMapper orderLineMapper;
    @Value("${test.cashier.fullName}")
    private String cashierFullName;
    @Value("${test.order.time}")
    private String orderCreatedAt;
    @Value("${test.product.name}")
    private String firstProductName;
    @Value("${test.product.name.second}")
    private String secondProductName;
    @Value("${test.order.cost}")
    private double orderCost;
    @Value("${test.cashier.id}")
    private long cashierId;
    private Cashier cashier;
    private Product firstProduct;
    private Product secondProduct;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @BeforeEach
    void setUp() {
        cashier = CashierTestBuilder.builder().withFullName(cashierFullName).build();
        firstProduct = ProductTestBuilder.builder().withId(1).withName(firstProductName).build();
        secondProduct = ProductTestBuilder.builder().withId(2).withName(secondProductName).build();
    }
    @Test
    void testToOrderDto() {
        Order order = buildOrder();
        when(calculatingService.getOrderTotalCost(order)).thenReturn(orderCost);
        OrderDto orderDto = mapper.toOrderDto(order);
        assertToOrderDto(order, orderDto);
    }
    @Test
    void testToOrder() {
        OrderAddingRequest request = buildRequest();
        when(cashierService.findById(anyLong())).thenReturn(cashier);
        List<OrderLine> orderLineList = Collections.nCopies(2, mock(OrderLine.class));
        when(orderLineMapper.toOrderLineList(any(OrderAddingRequest.class))).thenReturn(orderLineList);
        Order order = mapper.toOrder(request);
        assertToOrder(order, orderLineList);
    }
    private void assertToOrderDto(Order order, OrderDto orderDto) {
        assertEquals(orderDto.getDiscountPercent(), order.getDiscountPercent());
        assertEquals(orderDto.getCreatedAt(), order.getCreatedAt().format(formatter));
        assertEquals(orderDto.getTotalCost(), orderCost);
        assertEquals(orderDto.getCashierName(), order.getCashier().getFullName());
        assertEquals(orderDto.getOrderComposition().size(), 2);
        assertTrue(orderDto.getOrderComposition().containsKey(firstProductName));
        assertTrue(orderDto.getOrderComposition().containsKey(secondProductName));
    }
    private void assertToOrder(Order order, List<OrderLine> orderLineList) {
        assertEquals(order.getCreatedAt(), LocalDateTime.parse(orderCreatedAt, formatter));
        assertEquals(order.getCashier(), cashier);
        assertEquals(order.getComposition(), orderLineList);
    }
    private Order buildOrder() {
        LocalDateTime createdAt = LocalDateTime.parse(orderCreatedAt, formatter);
        return OrderTestBuilder.builder()
                .withCashier(cashier)
                .withCreatedAt(createdAt)
                .withComposition(List.of(
                        OrderLineTestBuilder.builder().withProduct(firstProduct).withQuantity(1).build(),
                        OrderLineTestBuilder.builder().withProduct(secondProduct).withQuantity(2).build()
                ))
                .build();
    }

    private OrderAddingRequest buildRequest() {
        return OrderAddingRequest.builder()
                .cashierId(cashierId)
                .createdAt(orderCreatedAt)
                .orderComposition(new HashMap<>() {{
                    put(1L, 1);
                    put(2L, 2);
                }})
                .build();
    }


}