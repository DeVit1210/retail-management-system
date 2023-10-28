package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplyDto;
import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.products.MaterialService;
import by.bsuir.retail.service.supply.SupplierService;
import by.bsuir.retail.testbuilder.impl.CoffeeShopTestBuilder;
import by.bsuir.retail.testbuilder.impl.MaterialTestBuilder;
import by.bsuir.retail.testbuilder.impl.SupplierTestBuilder;
import by.bsuir.retail.testbuilder.impl.SupplyTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class SupplyMapperTest {
    @InjectMocks
    private SupplyMapperImpl mapper;
    @Mock
    private CoffeeShopService coffeeShopService;
    @Mock
    private SupplierService supplierService;
    @Mock
    private MaterialService materialService;

    @Value("${test.coffeeShop.id}")
    private long coffeeShopId;

    @Value("${test.coffeeShop.name}")
    private String coffeeShopName;

    @Value("${test.supplier.id}")
    private long supplierId;

    @Value("${test.supplier.name}")
    private String supplierName;

    @Value("${test.material.name.first}")
    private String firstMaterialName;

    @Value("${test.material.name.second}")
    private String secondMaterialName;

    private Material firstMaterial;
    private Material secondMaterial;
    private CoffeeShop coffeeShop;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        firstMaterial = MaterialTestBuilder.builder().withId(1).withName(firstMaterialName).build();
        secondMaterial = MaterialTestBuilder.builder().withId(2).withName(secondMaterialName).build();
        coffeeShop = CoffeeShopTestBuilder.builder().withName(coffeeShopName).build();
        supplier = SupplierTestBuilder.builder().withCompanyName(supplierName).build();
    }

    @Test
    void testToSupplyDto() {
        Supply supply = buildSupply();
        SupplyDto supplyDto = mapper.toSupplyDto(supply);
        assertToSupplyDto(supply, supplyDto);
    }

    @Test
    void testToSupply() {
        SupplyAddingRequest supplyAddingRequest = buildRequest();
        when(coffeeShopService.findById(anyLong())).thenReturn(coffeeShop);
        when(supplierService.findById(anyLong())).thenReturn(supplier);
        when(materialService.findById(1L)).thenReturn(firstMaterial);
        when(materialService.findById(2L)).thenReturn(secondMaterial);
        Supply supply = mapper.toSupply(supplyAddingRequest);
        assertToSupply(supply);
    }

    private void assertToSupplyDto(Supply supply, SupplyDto supplyDto) {
        assertEquals(supplyDto.getCoffeeShopId(), supply.getCoffeeShop().getId());
        assertEquals(supplyDto.getCoffeeShopName(), supply.getCoffeeShop().getName());
        assertEquals(supplyDto.getSupplierName(), supply.getSupplier().getCompanyName());
        assertTrue(supplyDto.getSupplyComposition().containsKey(firstMaterialName));
        assertTrue(supplyDto.getSupplyComposition().containsKey(secondMaterialName));
    }
    private void assertToSupply(Supply supply) {
        assertEquals(supply.getCoffeeShop(), coffeeShop);
        assertEquals(supply.getSupplier(), supplier);
        assertTrue(supply.getComposition().containsKey(firstMaterial));
        assertTrue(supply.getComposition().containsKey(secondMaterial));
    }

    private Supply buildSupply() {
        Map<Material, Integer> composition = new HashMap<>() {{
            put(firstMaterial, 1);
            put(secondMaterial, 2);
        }};
        return SupplyTestBuilder.builder()
                .withCoffeeShop(coffeeShop)
                .withSupplier(supplier)
                .withComposition(composition)
                .build();
    }

    private SupplyAddingRequest buildRequest() {
        return SupplyAddingRequest.builder()
                .coffeeShopId(coffeeShopId)
                .supplierId(supplierId)
                .materialIdList(List.of(1L, 2L))
                .materialQuantityList(List.of(1, 2))
                .materialCostList(List.of(100.00, 200.00))
                .build();
    }
}