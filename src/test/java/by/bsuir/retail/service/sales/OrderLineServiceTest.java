package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.OrderLineMapper;
import by.bsuir.retail.repository.sales.OrderLineRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import by.bsuir.retail.testbuilder.impl.OrderLineTestBuilder;
import by.bsuir.retail.testbuilder.impl.OrderTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderLineServiceTest {
    @InjectMocks
    private OrderLineService orderLineService;
    @Mock
    private ResponseBuilder responseBuilder;
    @Mock
    private OrderLineRepository orderLineRepository;
    @Mock
    private OrderLineMapper mapper;
    @Test
    void getCoffeeShopOrderLineList() {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        List<OrderLine> orderLineList = buildOrderLineList(coffeeShop);
        when(orderLineRepository.findAllBySoldAtAfter(any(LocalDateTime.class))).thenReturn(orderLineList);
        List<OrderLine> result = orderLineService.getCoffeeShopOrderLineList(coffeeShop);
        verify(orderLineRepository, times(1))
                .findAllBySoldAtAfter(LocalDate.now().minusWeeks(1).atStartOfDay());
        assertEquals(2, result.size());
    }

    private List<OrderLine> buildOrderLineList(CoffeeShop coffeeShop) {
        CoffeeShop wrongCoffeeShop = mock(CoffeeShop.class);
        Cashier firstCashier = CashierTestBuilder.builder().withCoffeeShop(coffeeShop).build();
        Cashier secondCashier = CashierTestBuilder.builder().withCoffeeShop(wrongCoffeeShop).build();
        Order firstOrder = OrderTestBuilder.builder().withCashier(firstCashier).build();
        Order secondOrder = OrderTestBuilder.builder().withCashier(secondCashier).build();

        return List.of(
                OrderLineTestBuilder.builder().withOrder(firstOrder).build(),
                OrderLineTestBuilder.builder().withOrder(firstOrder).build(),
                OrderLineTestBuilder.builder().withOrder(secondOrder).build()
        );
    }
}