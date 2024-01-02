package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.OrderMapper;
import by.bsuir.retail.repository.sales.OrderRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.products.KitchenService;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final SpecificationService specificationService;
    private final KitchenService kitchenService;
    private final OrderLineService orderLineService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final ResponseBuilder responseBuilder;
    private final CoffeeShopService coffeeShopService;
    private final OrderMapper mapper;

    public Order findById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Order.class, orderId));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAll(SearchQueryRequest request) {
        return specificationService.executeQuery(request, orderRepository::findAll, Order.class);
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
        LocalDateTime endTime = Objects.isNull(shift.getEndTime()) ? LocalDateTime.MAX : shift.getEndTime();
        return orderRepository.findByCashierAndCreatedAtBetween(shift.getCashier(), shift.getStartTime(), endTime);
    }

    public double getOrderTotalCost(Order order) {
        Payment payment = order.getPayment();
        return paymentService.getPaymentTotalCost(payment);
    }

    public ResponseEntity<MultipleEntityResponse> getAllByCoffeeShop(long coffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(coffeeShopId);
        List<Cashier> cashierList = coffeeShop.getCashierList();
        List<Order> orderList = orderRepository.findAllByCashierIn(cashierList);
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderDtoList(orderList));
    }

}
