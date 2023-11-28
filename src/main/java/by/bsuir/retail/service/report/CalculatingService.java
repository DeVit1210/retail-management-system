package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.service.sales.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatingService {
    private final PaymentService paymentService;
    public double getOrderTotalCost(Order order) {
        Payment payment = paymentService.findByOrder(order);
        return paymentService.getPaymentTotalCost(payment);
    }
}
