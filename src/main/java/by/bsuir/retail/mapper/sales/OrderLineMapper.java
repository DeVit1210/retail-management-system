package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderLineDto;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.service.products.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderLineMapper {
    @Autowired
    protected ProductService productService;
    @Mapping(target = "productName", source = "product.name")
    public abstract OrderLineDto toOrderLineDto(OrderLine orderLine);
    @Mapping(target = "product", expression = "java(productService.findById(productId))")
    @Mapping(target = "soldAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "saleCost", expression = "java(productService.calculateProductCost(productId, discountPercent))")
    public abstract OrderLine toOrderLine(Long productId, Long orderId, int quantity, int discountPercent);
    public abstract List<OrderLineDto> toOrderLineDtoList(List<OrderLine> orderLineList);
    public List<OrderLine> toOrderLineList(OrderAddingRequest request, Order order) {
        final int discountPercent = request.getDiscountPercent();
        return request.getOrderComposition().entrySet()
                .stream()
                .map(entry -> toOrderLine(entry.getKey(), order.getId(), entry.getValue(), discountPercent))
                .toList();
    }
}
