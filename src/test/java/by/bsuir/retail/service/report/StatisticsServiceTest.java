package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.report.FinancialReportResponse;
import by.bsuir.retail.service.sales.OrderService;
import by.bsuir.retail.service.supply.SupplyService;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import by.bsuir.retail.testbuilder.impl.CoffeeShopTestBuilder;
import by.bsuir.retail.testbuilder.impl.OrderTestBuilder;
import by.bsuir.retail.testbuilder.impl.SupplyTestBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static by.bsuir.retail.entity.sales.profitability.FilterType.BY_COFFEE_SHOP;
import static by.bsuir.retail.entity.sales.profitability.FilterType.BY_COFFEE_SHOP_AND_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatisticsServiceTest {
    @InjectMocks
    private StatisticsService statisticsService;
    @Mock
    private OrderService orderService;
    @Mock
    private SupplyService supplyService;
    @Mock
    private ResponseBuilder responseBuilder;

    CoffeeShop firstCoffeeShop = CoffeeShopTestBuilder.builder().withId(firstCoffeeShopId).build();
    CoffeeShop secondCoffeeShop = CoffeeShopTestBuilder.builder().withId(secondCoffeeShopId).build();

    private static final long firstCoffeeShopId = 100;
    private static final long secondCoffeeShopId = 200;
    private static final double supplyCost = 50;
    private static final double orderCost = 100;

    static Stream<Arguments> financialReportTestProvider() {
        return Stream.of(
                Arguments.of(
                        FinancialReportResponse.builder()
                                .orderQuantity(3)
                                .supplyQuantity(4)
                                .totalIncome(orderCost * 3)
                                .totalExpenses(supplyCost * 4)
                                .build(),
                        null
                ),
                Arguments.of(
                        FinancialReportResponse.builder()
                                .orderQuantity(2)
                                .supplyQuantity(2)
                                .totalIncome(orderCost * 2)
                                .totalExpenses(supplyCost * 2)
                                .clearIncome(orderCost * 2 - supplyCost * 2)
                                .build(),
                        FinancialRequest.builder().coffeeShopId(firstCoffeeShopId).filterType(BY_COFFEE_SHOP.getType()).build()
                ),
                Arguments.of(
                        FinancialReportResponse.builder()
                                .orderQuantity(1)
                                .supplyQuantity(1)
                                .totalIncome(orderCost)
                                .totalExpenses(supplyCost)
                                .clearIncome(orderCost - supplyCost)
                                .build(),
                        FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .coffeeShopId(firstCoffeeShopId)
                                .filterType(BY_COFFEE_SHOP_AND_DATE.getType())
                                .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("financialReportTestProvider")
    void testGenerateFinancialReport(FinancialReportResponse expectedResult, FinancialRequest request) {
        List<Order> orderList = buildOrderList();
        List<Supply> supplyList = buildSupplyList();
        configureFinancialReportTestMocks(orderList, supplyList);
        var response = statisticsService.generateFinancialReport(request);
        assertEquals(expectedResult, Objects.requireNonNull(response.getBody()).getResponse());
    }

    private void configureFinancialReportTestMocks(List<Order> orderList, List<Supply> supplyList) {
        when(orderService.findAll()).thenReturn(orderList);
        when(orderService.getOrderTotalCost(any(Order.class))).thenReturn(orderCost);
        when(supplyService.findAll()).thenReturn(supplyList);
        when(responseBuilder.buildSingleEntityResponse(any(RetailManagementEntity.class))).thenCallRealMethod();
    }

    private List<Order> buildOrderList() {
        Cashier firstCoffeeShopCashier = CashierTestBuilder.builder().withCoffeeShop(firstCoffeeShop).build();
        Cashier secondCoffeeShopCashier = CashierTestBuilder.builder().withCoffeeShop(secondCoffeeShop).build();
        return List.of(
                OrderTestBuilder.builder()
                        .withCashier(firstCoffeeShopCashier).withCreatedAt(LocalDateTime.now().minusHours(48))
                        .build(),
                OrderTestBuilder.builder()
                        .withCashier(secondCoffeeShopCashier).withCreatedAt(LocalDateTime.now().minusHours(6))
                        .build(),
                OrderTestBuilder.builder()
                        .withCashier(firstCoffeeShopCashier).withCreatedAt(LocalDateTime.now().minusHours(6))
                        .build()
        );
    }

    private List<Supply> buildSupplyList() {
        return List.of(
                SupplyTestBuilder.builder()
                        .withCoffeeShop(firstCoffeeShop).withCreatedAt(LocalDateTime.now().minusHours(48))
                        .withTotalCost(supplyCost)
                        .build(),
                SupplyTestBuilder.builder()
                        .withCoffeeShop(firstCoffeeShop).withCreatedAt(LocalDateTime.now().minusHours(6))
                        .withTotalCost(supplyCost)
                        .build(),
                SupplyTestBuilder.builder()
                        .withCoffeeShop(secondCoffeeShop).withCreatedAt(LocalDateTime.now().minusHours(48))
                        .withTotalCost(supplyCost)
                        .build(),
                SupplyTestBuilder.builder()
                        .withCoffeeShop(secondCoffeeShop).withCreatedAt(LocalDateTime.now().minusHours(6))
                        .withTotalCost(supplyCost)
                        .build()
        );
    }
}