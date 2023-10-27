package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.mapper.sales.OrderMapper;
import by.bsuir.retail.repository.sales.OrderRepository;
import by.bsuir.retail.repository.sales.PaymentRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ResponseBuilder responseBuilder;
    private final OrderMapper mapper;
    public Order findById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Order.class, orderId));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toOrderDtoList(orderList));
    }
}
