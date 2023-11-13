package by.bsuir.retail.repository.supply;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.repository.products.MaterialRepository;
import by.bsuir.retail.testbuilder.impl.MaterialTestBuilder;
import by.bsuir.retail.testbuilder.impl.SupplyLineTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class SupplyLineRepositoryTest {
    @Autowired
    private SupplyLineRepository supplyLineRepository;
    @Autowired
    private MaterialRepository materialRepository;

    private final Material firstMaterial = MaterialTestBuilder.builder().build();
    private final Material secondMaterial = MaterialTestBuilder.builder().build();
    private final Material thirdMaterial = MaterialTestBuilder.builder().build();

    @BeforeEach
    void setUp() {
        materialRepository.saveAll(List.of(firstMaterial, secondMaterial, thirdMaterial));
    }

    @Test
    void testFindAllByMaterial() {
        supplyLineRepository.saveAll(List.of(
                SupplyLineTestBuilder.builder().withMaterial(firstMaterial).build(),
                SupplyLineTestBuilder.builder().withMaterial(secondMaterial).build(),
                SupplyLineTestBuilder.builder().withMaterial(firstMaterial).build()
        ));

        List<SupplyLine> withFirstMaterial = supplyLineRepository.findAllByMaterial(firstMaterial);
        List<SupplyLine> withSecondMaterial = supplyLineRepository.findAllByMaterial(secondMaterial);

        assertEquals(1, withFirstMaterial.size());
        assertEquals(2, withSecondMaterial.size());
    }

}