package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.mapper.sales.OrderMapper;
import by.bsuir.retail.repository.sales.OrderRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.products.KitchenService;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final SpecificationService specificationService;
    private final KitchenService kitchenService;
    private final OrderLineService orderLineService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final ResponseBuilder responseBuilder;
    private final OrderMapper mapper;
    public Order findById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Order.class, orderId));
    }
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public List<Order> findAll(SearchQueryRequest request) {
        Specification<Order> specificationChain = specificationService.createSpecificationChain(request, Order.class);
        return orderRepository.findAll(specificationChain);
    }
    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderDtoList(findAll()));
    }
    public ResponseEntity<MultipleEntityResponse> getAll(SearchQueryRequest request) {
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderDtoList(findAll(request)));
    }

    @Transactional
    public ResponseEntity<SingleEntityResponse> addOrder(OrderAddingRequest request) {
        Order order = mapper.toOrder(request);
        kitchenService.prepareOrder(order);
        Order savedOrder = orderRepository.save(order);
        orderLineService.createOrderLines(savedOrder);
        paymentService.createPayment(request, savedOrder);
        return responseBuilder.buildSingleEntityResponse(mapper.toOrderDto(savedOrder));
    }

    public ResponseEntity<SingleEntityResponse> getById(long orderId) {
        return responseBuilder.buildSingleEntityResponse(mapper.toOrderDto(findById(orderId)));
    }

    public double getShiftTotalIncome(Shift shift) {
        return findShiftOrderList(shift)
                .stream()
                .map(Order::getPayment)
                .map(paymentService::getPaymentTotalCost)
                .reduce(0.0, Double::sum);
    }

    public ResponseEntity<MultipleEntityResponse> getShiftOrderList(Shift shift) {
        List<Order> orderList = findShiftOrderList(shift);
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderDtoList(orderList));
    }

    private List<Order> findShiftOrderList(Shift shift) {
        return orderRepository.findByCashierAndCreatedAtBetween(shift.getCashier(), shift.getStartTime(), shift.getEndTime());
    }

    public double getOrderTotalCost(Order order) {
        Payment payment = order.getPayment();
        return paymentService.getPaymentTotalCost(payment);
    }

}
