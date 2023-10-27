package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderLineDto;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.service.products.ProductService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class OrderLineMapper {
    @Autowired
    protected ProductService productService;
    @Mapping(target = "productName", source = "product.name")
    public abstract OrderLineDto toOrderLineDto(OrderLine orderLine);
    @Mapping(target = "product", expression = "java(productService.findById(productId))")
    @Mapping(target = "soldAt", expression = "java(getCurrentTime())")
    @Mapping(target = "saleCost", expression = "java(productService.calculateProductCost(productId, discountPercent))")
    public abstract OrderLine toOrderLine(Long productId, int quantity, int discountPercent);
    @Named("currentTime")
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
