package by.bsuir.retail.service;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.repository.products.MaterialRepository;
import by.bsuir.retail.testbuilder.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class WarehouseServiceTest {
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private CoffeeShopService coffeeShopService;
    @Autowired
    private MaterialRepository materialRepository;
    private Material firstMaterial;
    private Material secondMaterial;
    private Material thirdMaterial;
    private CoffeeShop coffeeShop;

    @BeforeEach
    public void setUpMaterials() {
        firstMaterial = MaterialTestBuilder.builder().withId(1).build();
        secondMaterial = MaterialTestBuilder.builder().withId(2).build();
        thirdMaterial = MaterialTestBuilder.builder().withId(3).build();
        materialRepository.saveAll(List.of(firstMaterial, secondMaterial, thirdMaterial));
        coffeeShop = coffeeShopService.save(buildCoffeeShop());
    }

    @Test
    @Rollback
    void testUpdateWarehouseWithSupplySuccess() {
        Supply supply = buildSupply(coffeeShop);
        warehouseService.updateWarehouse(supply);
        CoffeeShop coffeeShopAfterSupply = coffeeShopService.findById(coffeeShop.getId());
        assertUpdateWarehouseWithSupply(coffeeShopAfterSupply);
    }

    @Test
    @Rollback
    void testUpdateWarehouseWithOrderSuccess() {
        Order order = buildOrder(coffeeShop);
        warehouseService.updateWarehouse(order);
        CoffeeShop coffeeShopAfterOrder = coffeeShopService.findById(coffeeShop.getId());
        assertUpdateWarehouseWithOrder(coffeeShopAfterOrder);
    }

    private CoffeeShop buildCoffeeShop() {
        Map<Material, Integer> warehouse = new HashMap<>() {{
            put(firstMaterial, 10);
            put(secondMaterial, 50);
        }};
        return CoffeeShopTestBuilder.builder().withWarehouse(warehouse).build();
    }

    private Supply buildSupply(CoffeeShop coffeeShop) {
        List<SupplyLine> supplyComposition = List.of(
                SupplyLineTestBuilder.builder().withMaterial(firstMaterial).withQuantity(10).build(),
                SupplyLineTestBuilder.builder().withMaterial(thirdMaterial).withQuantity(30).build()
        );

        return SupplyTestBuilder.builder().withCoffeeShop(coffeeShop).withComposition(supplyComposition).build();
    }

    private void assertUpdateWarehouseWithSupply(CoffeeShop result) {
        Map<Material, Integer> warehouseAfterSupply = result.getWarehouse();
        assertTrue(warehouseAfterSupply.containsKey(firstMaterial));
        assertEquals(warehouseAfterSupply.get(firstMaterial), 20);
        assertTrue(warehouseAfterSupply.containsKey(secondMaterial));
        assertEquals(warehouseAfterSupply.get(secondMaterial), 50);
        assertTrue(warehouseAfterSupply.containsKey(thirdMaterial));
        assertEquals(warehouseAfterSupply.get(thirdMaterial), 30);
    }

    private void assertUpdateWarehouseWithOrder(CoffeeShop coffeeShopAfterOrder) {
        Map<Material, Integer> warehouse = coffeeShopAfterOrder.getWarehouse();
        assertTrue(warehouse.containsKey(firstMaterial));
        assertEquals(warehouse.get(firstMaterial), 5);
        assertTrue(warehouse.containsKey(secondMaterial));
        assertEquals(warehouse.get(secondMaterial), 40);
        assertFalse(warehouse.containsKey(thirdMaterial));
    }

    private Order buildOrder(CoffeeShop savedCoffeeShop) {
        Map<Material, Integer> firstIngredients = Map.of(firstMaterial, 1, secondMaterial, 2);
        Map<Material, Integer> secondIngredients = Map.of(firstMaterial, 1, secondMaterial, 2);

        List<Product> productList = Stream.of(firstIngredients, secondIngredients)
                .map(composition -> TechProcessTestBuilder.builder().withIngredients(composition).build())
                .map(techProcess -> ProductTestBuilder.builder().withTechProcess(techProcess).build())
                .toList();

        List<OrderLine> orderComposition = List.of(
                OrderLineTestBuilder.builder().withProduct(productList.get(0)).withQuantity(2).build(),
                OrderLineTestBuilder.builder().withProduct(productList.get(1)).withQuantity(3).build()
        );
        
        Cashier cashier = CashierTestBuilder.builder().withCoffeeShop(savedCoffeeShop).build();

        return OrderTestBuilder.builder().withComposition(orderComposition).withCashier(cashier).build();
    }

}