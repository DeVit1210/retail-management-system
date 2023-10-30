package by.bsuir.retail.controller.product;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.request.products.ProductAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.testbuilder.impl.ProductTestBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private ProductService productService;
    @SneakyThrows
    @Test
    void testFindAllSuccess() {
        List<? extends RetailManagementEntity> productList = List.of(
                ProductTestBuilder.builder().withId(1).build(),
                ProductTestBuilder.builder().withId(2).build(),
                ProductTestBuilder.builder().withId(3).build()
        );
        MultipleEntityResponse response = MultipleEntityResponse.builder().response(productList).build();
        when(productService.findAll()).thenReturn(ResponseEntity.ok(response));
        mockMvc
                .perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(matcher -> assertEquals(matcher.getResponse().getContentAsString(), objectMapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @Test
    void testFindByIdSuccess() {
        Product product = ProductTestBuilder.builder().build();
        SingleEntityResponse response = SingleEntityResponse.builder().response(product).build();
        when(productService.getById(anyLong())).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(matcher -> assertEquals(matcher.getResponse().getContentAsString(), objectMapper.writeValueAsString(response)));
    }

    @SneakyThrows
    @Test
    @Disabled
    void testFindByIdNotFound() {
        when(productService.getById(anyLong())).thenThrow(WrongRetailEntityIdException.class);
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isNotFound())
                .andExpect(matcher -> assertTrue(matcher.getResolvedException() instanceof WrongRetailEntityIdException));
    }

    @SneakyThrows
    @Test
    @Disabled
    void testAddProductSuccess() {
        ProductAddingRequest request = ProductAddingRequest.builder().build();
        Product product = ProductTestBuilder.builder().build();
        SingleEntityResponse response = SingleEntityResponse.builder().response(product).build();
        when(productService.addProduct(request)).thenReturn(ResponseEntity.ok(response));
        mockMvc
                .perform(post("/product")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(matcher -> assertEquals(matcher.getResponse().getContentAsString(), objectMapper.writeValueAsString(response)));
    }
}