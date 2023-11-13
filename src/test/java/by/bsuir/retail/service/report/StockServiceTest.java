package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.report.StockReportResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.products.MaterialService;
import by.bsuir.retail.service.sales.OrderLineService;
import by.bsuir.retail.testbuilder.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StockServiceTest {
    @InjectMocks
    private StockService stockService;
    @Mock
    private ResponseBuilder responseBuilder;
    @Mock
    private CoffeeShopService coffeeShopService;
    @Mock
    private OrderLineService orderLineService;
    @Mock
    private MaterialService materialService;

    private static final Material firstMaterial = MaterialTestBuilder.builder().withId(1).build();

    private static final Material secondMaterial = MaterialTestBuilder.builder().withId(2).build();

    private static final Material thirdMaterial = MaterialTestBuilder.builder().withId(3).build();

    @ParameterizedTest
    @MethodSource("materialStockReportProvider")
    void testGenerateStockReportForMaterial(Material material,
                                            List<Integer> wareHouseLeftovers,
                                            List<Integer> expectedLeftDaysQuantity) {
        List<OrderLine> orderLineList = buildOrderLineList(material, wareHouseLeftovers);
        when(materialService.findById(anyLong())).thenReturn(material);
        when(orderLineService.findAll()).thenReturn(orderLineList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();
        ResponseEntity<MultipleEntityResponse> result = stockService.generateStockReportForMaterial(1L);
        List<StockReportResponse> expectedReportList = buildExpectedReportList(expectedLeftDaysQuantity);
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).getResponse().containsAll(expectedReportList));
    }

    @ParameterizedTest
    @MethodSource("coffeeShopStockReportProvider")
    void testGenerateStockReportForCoffeeShop(List<Integer> wareHouseLeftovers,
                                              List<Integer> expectedLeftDaysQuantity) {
        CoffeeShop coffeeShop = buildCoffeeShop(wareHouseLeftovers);
        List<OrderLine> orderLineList = buildOrderLineList(coffeeShop);
        when(coffeeShopService.findById(anyLong())).thenReturn(coffeeShop);
        when(orderLineService.getCoffeeShopOrderLineList(any(CoffeeShop.class))).thenReturn(orderLineList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();
        ResponseEntity<MultipleEntityResponse> result = stockService.generateStockReportForCoffeeShop(100);
        List<StockReportResponse> expectedReportList = buildExpectedReportList(expectedLeftDaysQuantity);
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).getResponse().containsAll(expectedReportList));
    }

    static Stream<Arguments> coffeeShopStockReportProvider() {
        return Stream.of(
                Arguments.of(List.of(100, 100, 100), List.of(36, 18, 12)),
                Arguments.of(List.of(10, 10, 10), List.of(4, 2, 2)),
                Arguments.of(List.of(100, 100, 0), List.of(36, 18, 0))
        );
    }

    static Stream<Arguments> materialStockReportProvider() {
        return Stream.of(
                Arguments.of(firstMaterial, List.of(100, 100, 100), List.of(71, 36, 71)),
                Arguments.of(secondMaterial, List.of(100, 100, 100), List.of(18, 36, 36)),
                Arguments.of(thirdMaterial, List.of(100, 100, 100), List.of(24, 24, 12))
        );
    }

    private List<StockReportResponse> buildExpectedReportList(List<Integer> expectedLeftDaysQuantity) {
        return expectedLeftDaysQuantity.stream()
                .map(daysQuantity -> StockReportResponse.builder()
                        .expirationDate(LocalDate.now().plusDays(daysQuantity))
                        .build()
                )
                .toList();
    }

    private List<OrderLine> buildOrderLineList(CoffeeShop coffeeShop) {
        Cashier cashier = CashierTestBuilder.builder().withCoffeeShop(coffeeShop).build();
        Order order = OrderTestBuilder.builder().withCashier(cashier).build();

        return buildIngredientsStream()
                .map(composition -> TechProcessTestBuilder.builder().withIngredients(composition).build())
                .map(techProcess -> ProductTestBuilder.builder().withTechProcess(techProcess).build())
                .map(product -> OrderLineTestBuilder.builder().withProduct(product).withQuantity(10).withOrder(order).build())
                .toList();
    }

    private List<OrderLine> buildOrderLineList(Material material, List<Integer> warehouseLeftovers) {
        List<Product> productList = buildIngredientsStream()
                .map(composition -> TechProcessTestBuilder.builder().withIngredients(composition).build())
                .map(techProcess -> ProductTestBuilder.builder().withTechProcess(techProcess).build())
                .toList();

        List<Order> orderList = warehouseLeftovers.stream()
                .map(leftover -> buildCoffeeShop(material, leftover))
                .map(coffeeShop -> CashierTestBuilder.builder().withCoffeeShop(coffeeShop).build())
                .map(cashier -> OrderTestBuilder.builder().withCashier(cashier).build())
                .toList();

        return List.of(
                buildOrderLine(productList.get(0), orderList.get(0)),
                buildOrderLine(productList.get(1), orderList.get(1)),
                buildOrderLine(productList.get(2), orderList.get(2)),
                buildOrderLine(productList.get(2), orderList.get(0)),
                buildOrderLine(productList.get(0), orderList.get(1)),
                buildOrderLine(productList.get(1), orderList.get(2))
        );
    }

    private CoffeeShop buildCoffeeShop(Material material, Integer leftover) {
        return CoffeeShopTestBuilder.builder().withWarehouse(Map.of(material, leftover)).build();
    }

    private CoffeeShop buildCoffeeShop(List<Integer> wareHouseLeftovers) {
        Map<Material, Integer> warehouse = new HashMap<>() {{
            put(firstMaterial, wareHouseLeftovers.get(0));
            put(secondMaterial, wareHouseLeftovers.get(1));
            put(thirdMaterial, wareHouseLeftovers.get(2));
        }};
        return CoffeeShopTestBuilder.builder().withId(100).withWarehouse(warehouse).build();
    }

    private Stream<Map<Material, Integer>> buildIngredientsStream() {
        Map<Material, Integer> firstComposition = Map.of(firstMaterial, 1, secondMaterial, 2);
        Map<Material, Integer> secondComposition = Map.of(firstMaterial, 1, thirdMaterial, 3);
        Map<Material, Integer> thirdComposition = Map.of(secondMaterial, 2, thirdMaterial, 3);
        return Stream.of(firstComposition, secondComposition, thirdComposition);
    }

    private OrderLine buildOrderLine(Product product, Order order) {
        return OrderLineTestBuilder.builder()
                .withProduct(product).withQuantity(10).withOrder(order)
                .build();
    }

}