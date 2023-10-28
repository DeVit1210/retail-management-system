package by.bsuir.retail.mapper.products;

import by.bsuir.retail.dto.products.TechProcessDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.request.products.TechProcessAddingRequest;
import by.bsuir.retail.service.products.MaterialService;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.testbuilder.impl.MaterialTestBuilder;
import by.bsuir.retail.testbuilder.impl.ProductTestBuilder;
import by.bsuir.retail.testbuilder.impl.TechProcessTestBuilder;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class TechProcessMapperTest {
    @InjectMocks
    private TechProcessMapperImpl mapper;
    @Mock
    private ProductService productService;
    @Mock
    private MaterialService materialService;
    @Value("${test.product.name}")
    private String productName;
    @Value("${test.product.weight}")
    private int productWeight;
    @Value("${test.material.name.first}")
    private String firstMaterialName;
    @Value("${test.material.name.second}")
    private String secondMaterialName;
    private Product product;
    private Material firstMaterial;
    private Material secondMaterial;

    @BeforeEach
    void setUp() {
        product = ProductTestBuilder.builder().withName(productName).withWeight(productWeight).build();
        firstMaterial = MaterialTestBuilder.builder().withId(1).withName(firstMaterialName).build();
        secondMaterial = MaterialTestBuilder.builder().withId(2).withName(secondMaterialName).build();
    }

    @Test
    void testToTechProcessDto() {
        TechProcess techProcess = buildTechProcess();
        TechProcessDto techProcessDto = mapper.toTechProcessDto(techProcess);
        assertToTechProcessDto(techProcessDto, techProcess);
    }

    @Test
    void testToTechProcess() {
        TechProcessAddingRequest request = buildRequest();
        when(productService.findById(anyLong())).thenReturn(product);
        when(materialService.findById(1L)).thenReturn(firstMaterial);
        when(materialService.findById(2L)).thenReturn(secondMaterial);
        TechProcess techProcess = mapper.toTechProcess(request);
        assertToTechProcess(techProcess, request);
    }

    private TechProcessAddingRequest buildRequest() {
        return TechProcessAddingRequest.builder()
                .productId(1)
                .processComposition(new HashMap<>() {{
                    put(1L, 1);
                    put(2L, 2);
                }})
                .build();
    }

    private TechProcess buildTechProcess() {
        Map<Material, Integer> ingredients = new HashMap<>() {{
            put(firstMaterial, 1);
            put(secondMaterial, 2);
        }};
        return TechProcessTestBuilder.builder()
                .withProduct(product)
                .withIngredients(ingredients)
                .build();
    }

    private void assertToTechProcessDto(TechProcessDto techProcessDto, TechProcess techProcess) {
        assertEquals(techProcessDto.getProcessId(), techProcess.getId());
        assertEquals(techProcessDto.getCreatedProductName(), productName);
        assertEquals(techProcessDto.getCreatedProductWeight(), productWeight);
        assertEquals(2, techProcessDto.getIngredientNameAndQuantity().size());
        assertTrue(techProcessDto.getIngredientNameAndQuantity().containsKey(firstMaterialName));
        assertTrue(techProcessDto.getIngredientNameAndQuantity().containsKey(secondMaterialName));
    }

    private void assertToTechProcess(TechProcess techProcess, TechProcessAddingRequest request) {
        assertEquals(techProcess.getCreatedProduct(), product);
        assertEquals(techProcess.getIngredients().size(), 2);
        assertTrue(techProcess.getIngredients().containsKey(firstMaterial));
        assertTrue(techProcess.getIngredients().containsKey(secondMaterial));
    }


}