package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderDto;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.service.CalculatingService;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.service.users.CashierService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Autowired
    protected CalculatingService calculatingService;
    @Autowired
    protected CashierService cashierService;
    @Autowired
    protected ProductService productService;
    @Mapping(target = "cashierName", source = "order.cashier.fullName")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "orderComposition", expression = "java(mapOrderComposition(order))")
    @Mapping(target = "totalCost", expression = "java(calculatingService.getOrderTotalCost(order))")
    public abstract OrderDto toOrderDto(Order order);

    @Mapping(target = "cashier", expression = "java(cashierService.findById(request.getCashierId()))")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "composition", expression = "java(mapOrderComposition(request))")
    public abstract Order toOrder(OrderAddingRequest request);

    public abstract List<OrderDto> toOrderDtoList(List<Order> orderList);

    protected Map<String, Integer> mapOrderComposition(Order order) {
        return order.getComposition().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
    }

    protected Map<Product, Integer> mapOrderComposition(OrderAddingRequest request) {
        return request.getOrderComposition().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productService.findById(entry.getKey()), Map.Entry::getValue));
    }
}
