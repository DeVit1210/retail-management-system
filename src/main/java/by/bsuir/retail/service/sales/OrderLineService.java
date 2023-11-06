package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.mapper.sales.OrderLineMapper;
import by.bsuir.retail.repository.sales.OrderLineRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final SpecificationService specificationService;
    private final OrderLineRepository orderLineRepository;
    private final ResponseBuilder responseBuilder;
    private final OrderLineMapper mapper;
    public OrderLine findById(long orderLineId) {
        return orderLineRepository.findById(orderLineId)
                .orElseThrow(() -> new WrongRetailEntityIdException(OrderLine.class, orderLineId));
    }
    public List<OrderLine> findAll() {
        return orderLineRepository.findAll();
    }

    public List<OrderLine> findAll(SearchQueryRequest request) {
        Specification<OrderLine> specificationChain =
                specificationService.createSpecificationChain(request, OrderLine.class);
        return orderLineRepository.findAll(specificationChain);
    }

    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderLineDtoList(findAll()));
    }

    private ResponseEntity<MultipleEntityResponse> getAll(SearchQueryRequest request) {
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderLineDtoList(findAll(request)));
    }

    public void createOrderLines(Order order) {
        order.getComposition().forEach(orderLine -> orderLine.setOrder(order));
        orderLineRepository.saveAll(order.getComposition());
    }

    public ResponseEntity<SingleEntityResponse> getById(long orderLineId) {
        return responseBuilder.buildSingleEntityResponse(findById(orderLineId));
    }

    public List<OrderLine> getCoffeeShopOrderLineList(CoffeeShop coffeeShop) {
        LocalDateTime startSearchingTime = LocalDateTime.now().minusWeeks(1);
        return orderLineRepository.findAllBySoldAtAfter(startSearchingTime).stream()
                .filter(orderLine -> this.isOrderLineInCoffeeShop(orderLine, coffeeShop))
                .toList();
    }

    private boolean isOrderLineInCoffeeShop(OrderLine orderLine, CoffeeShop coffeeShop) {
        return orderLine.getOrder().getCashier().getCoffeeShop().equals(coffeeShop);
    }
}
