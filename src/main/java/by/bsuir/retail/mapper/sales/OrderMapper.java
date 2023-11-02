package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.OrderDto;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
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
    protected OrderLineMapper orderLineMapper;
    @Mapping(target = "cashierName", source = "order.cashier.fullName")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "orderComposition", expression = "java(mapOrderComposition(order))")
    @Mapping(target = "totalCost", expression = "java(calculatingService.getOrderTotalCost(order))")
    public abstract OrderDto toOrderDto(Order order);

    @Mapping(target = "cashier", expression = "java(cashierService.findById(request.getCashierId()))")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "composition", expression = "java(orderLineMapper.toOrderLineList(request))")
    public abstract Order toOrder(OrderAddingRequest request);

    public abstract List<OrderDto> toOrderDtoList(List<Order> orderList);

    protected Map<String, Integer> mapOrderComposition(Order order) {
        return order.getComposition().stream()
                .collect(Collectors.toMap(orderLine -> orderLine.getProduct().getName(), OrderLine::getQuantity));
    }
}
