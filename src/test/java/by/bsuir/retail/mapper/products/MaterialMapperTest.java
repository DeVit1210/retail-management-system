package by.bsuir.retail.mapper.products;

import by.bsuir.retail.dto.products.MaterialDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.request.products.MaterialAddingRequest;
import by.bsuir.retail.testbuilder.impl.MaterialTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MaterialMapperTest {
    @Autowired
    private MaterialMapper mapper;
    @Test
    void testToMaterialDto() {
        Material material = MaterialTestBuilder.builder().build();
        final String expectedMaterialName = material.getName();
        double expectedPurchaseCost = material.getPurchaseCost();
        MaterialDto materialDto = mapper.toMaterialDto(material);
        assertEquals(expectedPurchaseCost, materialDto.getPurchaseCost());
        assertEquals(expectedMaterialName, materialDto.getName());
    }

    @Test
    void testToMaterial() {
        final String name = "name";
        final int weight = 100;
        MaterialAddingRequest request = MaterialAddingRequest.builder().name(name).weight(weight).build();
        Material material = mapper.toMaterial(request);
        assertEquals(name, material.getName());
        assertEquals(weight, material.getWeight());
    }

    @Test
    void testToMaterialDtoList() {
        List<Material> materialList = List.of(
                MaterialTestBuilder.builder().withName("first").build(),
                MaterialTestBuilder.builder().withName("second").build(),
                MaterialTestBuilder.builder().withName("third").build()
        );
        List<MaterialDto> materialDtoList = mapper.toMaterialDtoList(materialList);
        assertEquals("first", materialDtoList.get(0).getName());
        assertEquals("second", materialDtoList.get(1).getName());
        assertEquals("third", materialDtoList.get(2).getName());

    }
}