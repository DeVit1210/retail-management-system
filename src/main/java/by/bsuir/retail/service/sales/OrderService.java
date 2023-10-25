package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.repository.sales.OrderRepository;
import by.bsuir.retail.repository.sales.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public Order findById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order with such id was not found: " + orderId));
    }

    public double getOrderTotalCost(Order order) {
        Payment payment = order.getPayment();
        return paymentService.getPaymentTotalCost(payment);
    }
}
