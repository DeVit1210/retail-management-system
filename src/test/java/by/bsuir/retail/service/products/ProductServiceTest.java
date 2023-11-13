package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.repository.products.ProductRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.testbuilder.impl.ProductTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Test
    void testFindByIdSuccess() {
        Product product = ProductTestBuilder.builder().build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        assertDoesNotThrow(() -> productService.findById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrowsExactly(WrongRetailEntityIdException.class, () -> productService.findById(1L));
    }

    static Stream<Arguments> calculateCostArgumentsProvider() {
        return Stream.of(
                Arguments.of(100, 5, 95),
                Arguments.of(10, 10, 9),
                Arguments.of(70, 0, 70)
        );
    }

    @ParameterizedTest
    @MethodSource("calculateCostArgumentsProvider")
    void testCalculateTotalCost(double saleCost, int discountPercent, double finalCost) {
        Product product = ProductTestBuilder.builder().withSaleCost(saleCost).build();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        double result = productService.calculateProductCost(1L, discountPercent);
        assertEquals(finalCost, result);
    }

}