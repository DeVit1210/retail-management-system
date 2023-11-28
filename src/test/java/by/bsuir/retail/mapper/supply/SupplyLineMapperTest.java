package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplyLineDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.request.supply.SupplyLineAddingRequest;
import by.bsuir.retail.service.products.MaterialService;
import by.bsuir.retail.testbuilder.impl.MaterialTestBuilder;
import by.bsuir.retail.testbuilder.impl.SupplyLineTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class SupplyLineMapperTest {
    @InjectMocks
    private SupplyLineMapperImpl mapper;
    @Mock
    private MaterialService materialService;

    @Value("${test.material.name.first}")
    private String materialName;

    @Value("${test.material.id}")
    private long materialId;

    @Value("${test.material.quantity}")
    private int materialQuantity;

    @Value("${test.material.cost}")
    private double materialCost;
    @Test
    void testToSupplyLineDto() {
        Material material = MaterialTestBuilder.builder().withName(materialName).build();
        SupplyLine supplyLine = SupplyLineTestBuilder.builder().withMaterial(material).build();
        SupplyLineDto supplyLineDto = mapper.toSupplyLineDto(supplyLine);
        assertEquals(materialName, supplyLineDto.getMaterialName());
    }

    @Test
    void testToSupplyLine() {
        Material material = MaterialTestBuilder.builder().build();
        SupplyLineAddingRequest request = SupplyLineAddingRequest.builder()
                .materialId(materialId)
                .quantity(materialQuantity)
                .purchaseCost(materialCost)
                .build();
        Mockito.when(materialService.findById(materialId)).thenReturn(material);
        SupplyLine supplyLine = mapper.toSupplyLine(request);
        assertEquals(supplyLine.getMaterial(), material);
        assertEquals(supplyLine.getPurchaseCost(), materialCost);
        assertEquals(supplyLine.getQuantity(), materialQuantity);
        assertNotNull(supplyLine.getPurchasedAt());
    }

}