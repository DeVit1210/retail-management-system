package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.response.report.ProfitabilityResponse;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.service.supply.SupplyLineService;
import by.bsuir.retail.testbuilder.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static by.bsuir.retail.entity.sales.profitability.FilterType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ProfitabilityServiceTest {
    @InjectMocks
    private ProfitabilityService profitabilityService;
    @Mock
    private SupplyLineService supplyLineService;
    @Mock
    private ProductService productService;
    @Mock
    private ResponseBuilder responseBuilder;

    private static final long firstCoffeeShopId = 100;
    private static final long secondCoffeeShopId = 200;
    private static final long firstProductId = 1;
    private static final long secondProductId = 2;
    private static final long thirdProductId = 3;

    private Product firstProduct;
    private Product secondProduct;
    private Product thirdProduct;
    @ParameterizedTest
    @MethodSource("calculateTotalProfitabilityProvider")
    void testCalculateProfitability(List<ProfitabilityResponse> expectedResults, FinancialRequest request) {
        configureCalculateTotalProfitabilityMocks();
        ResponseEntity<MultipleEntityResponse> result = profitabilityService.calculateProfitability(request);
        List<? extends RetailManagementEntity> resultList = Objects.requireNonNull(result.getBody()).getResponse();
        System.out.println(resultList);
        assertTrue(resultList.containsAll(expectedResults));
    }

    @ParameterizedTest
    @MethodSource("calculateProductProfitabilityProvider")
    void testCalculateProductProfitability(ProfitabilityResponse expectedResult, long productId, FinancialRequest request) {
        configureCalculateProductProfitabilityMocks();
        ResponseEntity<SingleEntityResponse> result = profitabilityService.calculateProfitability(productId, request);
        System.out.println(result.getBody().getResponse());
        assertEquals(expectedResult, Objects.requireNonNull(result.getBody().getResponse()));
    }


    private void configureCalculateTotalProfitabilityMocks() {
        List<Product> productList = List.of(firstProduct, secondProduct, thirdProduct);
        when(productService.findAll()).thenReturn(productList);
        when(responseBuilder.buildMultipleEntityResponse(anyList())).thenCallRealMethod();
    }

    private void configureCalculateProductProfitabilityMocks() {
        when(productService.findById(firstProductId)).thenReturn(firstProduct);
        when(productService.findById(secondProductId)).thenReturn(secondProduct);
        when(productService.findById(thirdProductId)).thenReturn(thirdProduct);
        when(responseBuilder.buildSingleEntityResponse(any(RetailManagementEntity.class))).thenCallRealMethod();
    }

    static Stream<Arguments> calculateProductProfitabilityProvider() {
        return Stream.of(
                Arguments.of(new ProfitabilityResponse( 10.0, 5.0), firstProductId, null),
                Arguments.of(new ProfitabilityResponse( 20.0, 7.0), secondProductId, null),
                Arguments.of(new ProfitabilityResponse( 30.0, 15.0), thirdProductId, null),
                Arguments.of(
                        new ProfitabilityResponse( 10.0, 6.5),
                        firstProductId,
                        FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .coffeeShopId(firstCoffeeShopId)
                                .filterType(BY_COFFEE_SHOP_AND_DATE.getType())
                                .build()
                ),
                Arguments.of(
                        new ProfitabilityResponse( 0.0, 7.5),
                        firstProductId,
                        FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .coffeeShopId(secondCoffeeShopId)
                                .filterType(BY_COFFEE_SHOP_AND_DATE.getType())
                                .build()
                ),
                Arguments.of(
                        new ProfitabilityResponse( 0.0, 16.5),
                        thirdProductId,
                        FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .coffeeShopId(firstCoffeeShopId)
                                .filterType(BY_COFFEE_SHOP_AND_DATE.getType())
                                .build()
                ),
                Arguments.of(
                        new ProfitabilityResponse( 30.0, 19.0),
                        thirdProductId,
                        FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .coffeeShopId(secondCoffeeShopId)
                                .filterType(BY_COFFEE_SHOP_AND_DATE.getType())
                                .build()
                )
        );
    }


    static Stream<Arguments> calculateTotalProfitabilityProvider() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new ProfitabilityResponse( 10.0, 5.0),
                                new ProfitabilityResponse( 20.0, 7.0),
                                new ProfitabilityResponse( 30.0, 15.0)
                        ), null
                ),
                Arguments.of(
                        List.of(
                                new ProfitabilityResponse( 10.0, 5.0),
                                new ProfitabilityResponse( 20.0, 6.0),
                                new ProfitabilityResponse( 0.0, 13.5)
                        ), FinancialRequest.builder().coffeeShopId(firstCoffeeShopId).filterType(BY_COFFEE_SHOP.getType()).build()
                ),
                Arguments.of(
                        List.of(
                                new ProfitabilityResponse( 0.0, 7.5),
                                new ProfitabilityResponse( 0.0, 8.5),
                                new ProfitabilityResponse( 30.0, 19.0)
                        ), FinancialRequest.builder().coffeeShopId(secondCoffeeShopId).filterType(BY_COFFEE_SHOP.getType()).build()
                ),
                Arguments.of(
                        List.of(
                                new ProfitabilityResponse( 10.0, 6.5),
                                new ProfitabilityResponse( 0.0, 7.5),
                                new ProfitabilityResponse( 0.0, 16.5)
                        ), FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .coffeeShopId(firstCoffeeShopId)
                                .filterType(BY_COFFEE_SHOP_AND_DATE.getType())
                                .build()
                ),
                Arguments.of(
                        List.of(
                                new ProfitabilityResponse( 10.0, 6.5),
                                new ProfitabilityResponse( 0.0, 8.5),
                                new ProfitabilityResponse( 30.0, 18.0)
                        ), FinancialRequest.builder()
                                .startTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now().minusDays(1)))
                                .endTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                                .filterType(BY_DATE.getType())
                                .build()
                )
        );
    }

    @BeforeEach
    public void buildProductList() {
        Material firstMaterial = MaterialTestBuilder.builder().withId(1).withPurchaseCost(2.5).build();
        Material secondMaterial = MaterialTestBuilder.builder().withId(2).withPurchaseCost(2.5).build();
        Material thirdMaterial = MaterialTestBuilder.builder().withId(3).withPurchaseCost(2.5).build();

        Map<Material, Integer> firstComposition = new HashMap<>() {{
            put(firstMaterial, 1);
            put(secondMaterial, 2);
        }};

        Map<Material, Integer> secondComposition = new HashMap<>() {{
            put(firstMaterial, 1);
            put(thirdMaterial, 2);
        }};

        Map<Material, Integer> thirdComposition = new HashMap<>() {{
            put(firstMaterial, 2);
            put(secondMaterial, 2);
            put(thirdMaterial, 3);
        }};

        TechProcess firstTechProcess = TechProcessTestBuilder.builder().withIngredients(firstComposition).build();
        TechProcess secondTechProcess = TechProcessTestBuilder.builder().withIngredients(secondComposition).build();
        TechProcess thirdTechProcess = TechProcessTestBuilder.builder().withIngredients(thirdComposition).build();

        CoffeeShop firstCoffeeShop = CoffeeShopTestBuilder.builder().withId(firstCoffeeShopId).build();
        Cashier firstCashier = CashierTestBuilder.builder().withCoffeeShop(firstCoffeeShop).build();
        Order firstOrder = OrderTestBuilder.builder().withCashier(firstCashier).build();
        Supply firstSupply = SupplyTestBuilder.builder().withCoffeeShop(firstCoffeeShop).build();

        CoffeeShop secondCoffeeShop = CoffeeShopTestBuilder.builder().withId(secondCoffeeShopId).build();
        Cashier secondCashier = CashierTestBuilder.builder().withCoffeeShop(secondCoffeeShop).build();
        Order secondOrder = OrderTestBuilder.builder().withCashier(secondCashier).build();
        Supply secondSupply = SupplyTestBuilder.builder().withCoffeeShop(secondCoffeeShop).build();

        SupplyLine firstSupplyLine = buildSupplyLine(firstMaterial, firstSupply, 1, 48);
        SupplyLine secondSupplyLine = buildSupplyLine(secondMaterial, firstSupply, 2, 12);
        SupplyLine thirdSupplyLine = buildSupplyLine(thirdMaterial, secondSupply, 3, 6);

        when(supplyLineService.findAllByMaterial(firstMaterial)).thenReturn(List.of(firstSupplyLine));
        when(supplyLineService.findAllByMaterial(secondMaterial)).thenReturn(List.of(secondSupplyLine));
        when(supplyLineService.findAllByMaterial(thirdMaterial)).thenReturn(List.of(thirdSupplyLine));

        OrderLine firstOrderLine = buildOrderLine(firstOrder, 10.0, 1, 6);
        OrderLine secondOrderLine = buildOrderLine(firstOrder, 20.0, 2, 48);
        OrderLine thirdOrderLine = buildOrderLine(secondOrder, 30.0, 3, 12);

        firstProduct = buildProduct(firstTechProcess, List.of(firstOrderLine));
        secondProduct = buildProduct(secondTechProcess, List.of(secondOrderLine));
        thirdProduct = buildProduct(thirdTechProcess, List.of(thirdOrderLine));
    }

    private static SupplyLine buildSupplyLine(Material material, Supply supply, double purchasedCost, int createdHoursAway) {
        return SupplyLineTestBuilder.builder()
                .withMaterial(material).withSupply(supply).withQuantity(10)
                .withPurchaseCost(purchasedCost).withPurchasedAt(LocalDateTime.now().minusHours(createdHoursAway))
                .build();
    }

    private static OrderLine buildOrderLine(Order order, double saleCost, int quantity, int createdHoursAway) {
        return OrderLineTestBuilder.builder()
                .withOrder(order).withSaleCost(saleCost)
                .withQuantity(quantity).withSoldAt(LocalDateTime.now().minusHours(createdHoursAway))
                .build();
    }

    private static Product buildProduct(TechProcess techProcess, List<OrderLine> orderLines) {
        return ProductTestBuilder.builder()
                .withTechProcess(techProcess).withOrderLines(orderLines)
                .build();
    }

}