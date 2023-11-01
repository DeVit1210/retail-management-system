package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.mapper.sales.OrderLineMapper;
import by.bsuir.retail.repository.sales.OrderLineRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<OrderLine> orderLineList = orderLineRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderLineDtoList(orderLineList));
    }

    private ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<OrderLine> specificationChain =
                specificationService.createSpecificationChain(request, OrderLine.class);
        List<OrderLine> orderLineList = orderLineRepository.findAll(specificationChain);
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderLineDtoList(orderLineList));
    }

    public void createOrderLines(OrderAddingRequest request, Order order) {
        List<OrderLine> orderLineList = mapper.toOrderLineList(request, order);
        orderLineRepository.saveAll(orderLineList);
    }

    public ResponseEntity<SingleEntityResponse> getById(long orderLineId) {
        return responseBuilder.buildSingleEntityResponse(findById(orderLineId));
    }

    public List<OrderLine> findAllByProduct(Product product) {
        return orderLineRepository.findAllByProduct(product);
    }
}
