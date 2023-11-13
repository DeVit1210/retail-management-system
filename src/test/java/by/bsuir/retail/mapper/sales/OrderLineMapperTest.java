package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderLineDto;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.testbuilder.impl.OrderLineTestBuilder;
import by.bsuir.retail.testbuilder.impl.ProductTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class OrderLineMapperTest {
    @InjectMocks
    private OrderLineMapperImpl mapper;
    @Mock
    private ProductService productService;
    @Value("${test.product.name}")
    private String productName;
    @Value("${test.product.name.second}")
    private String secondProductName;
    @Value("${test.product.cost}")
    private double productCost;
    @Test
    void testToOrderLineDto() {
        Product product = ProductTestBuilder.builder().withName(productName).build();
        OrderLine orderLine = OrderLineTestBuilder.builder().withProduct(product).build();
        OrderLineDto orderLineDto = mapper.toOrderLineDto(orderLine);
        assertEquals(productName, orderLineDto.getProductName());
    }
    @Test
    void testToOrderDto() {
        Product product = ProductTestBuilder.builder().withName(productName).build();
        when(productService.findById(anyLong())).thenReturn(product);
        when(productService.calculateProductCost(anyLong(), anyInt())).thenReturn(productCost);
        OrderLine orderLine = mapper.toOrderLine(1L, 1, 1);
        assertEquals(orderLine.getProduct(), product);
        assertEquals(orderLine.getSaleCost(), productCost);
    }

    @Test
    void testToOrderLineList() {
        OrderAddingRequest request = buildRequest();
        Product firstProduct = ProductTestBuilder.builder().withName(productName).build();
        Product secondProduct = ProductTestBuilder.builder().withName(secondProductName).build();
        when(productService.findById(1L)).thenReturn(firstProduct);
        when(productService.findById(2L)).thenReturn(secondProduct);
        when(productService.calculateProductCost(anyLong(), anyInt())).thenReturn(productCost);
        List<OrderLine> orderLineList = mapper.toOrderLineList(request);
        assertEquals(2, orderLineList.size());
        assertEquals(orderLineList.get(0).getProduct(), firstProduct);
        assertEquals(orderLineList.get(1).getProduct(), secondProduct);
    }

    private OrderAddingRequest buildRequest() {
        return OrderAddingRequest.builder()
                .discountPercent(10)
                .orderComposition(new HashMap<>() {{
                    put(1L, 1);
                    put(2L, 2);
                }})
                .build();
    }
}