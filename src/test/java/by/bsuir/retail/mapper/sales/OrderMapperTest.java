package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderDto;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.service.report.CalculatingService;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.service.users.CashierService;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

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
    private ProductService productService;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
        when(productService.findById(1L)).thenReturn(firstProduct);
        when(productService.findById(2L)).thenReturn(secondProduct);
        Order order = mapper.toOrder(request);
        assertToOrder(order);
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
    private void assertToOrder(Order order) {
        assertEquals(order.getCreatedAt(), LocalDateTime.parse(orderCreatedAt, formatter));
        assertEquals(order.getCashier(), cashier);
        assertEquals(order.getComposition().size(), 2);
        assertTrue(order.getComposition().containsKey(firstProduct));
        assertTrue(order.getComposition().containsKey(secondProduct));
    }
    private Order buildOrder() {
        LocalDateTime createdAt = LocalDateTime.parse(orderCreatedAt, formatter);
        return OrderTestBuilder.builder()
                .withCashier(cashier)
                .withCreatedAt(createdAt)
                .withComposition(new HashMap<>() {{
                    put(firstProduct, 1);
                    put(secondProduct, 1);
                }})
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