package by.bsuir.retail.mapper.products;

import by.bsuir.retail.dto.products.ProductDto;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.request.products.ProductAddingRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toProduct(ProductAddingRequest request);
    List<ProductDto> toProductDtoList(List<Product> productList);
}
