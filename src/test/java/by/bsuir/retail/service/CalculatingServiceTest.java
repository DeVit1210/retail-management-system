package by.bsuir.retail.service;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.service.report.CalculatingService;
import by.bsuir.retail.service.sales.PaymentService;
import by.bsuir.retail.testbuilder.impl.OrderTestBuilder;
import by.bsuir.retail.testbuilder.impl.PaymentTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class CalculatingServiceTest {
    @InjectMocks
    private CalculatingService calculatingService;
    @Mock
    private PaymentService paymentService;
    @Value("${test.payment.cash}")
    private double paidInCash;
    @Value("${test.payment.card}")
    private double paidInCard;
    @Test
    void testGetOrderTotalCost() {
        Payment payment = PaymentTestBuilder.builder().withPaidInCard(paidInCard).withPaidInCash(paidInCash).build();
        Order order = OrderTestBuilder.builder().withPayment(payment).build();
        when(paymentService.findByOrder(any(Order.class))).thenReturn(payment);
        when(paymentService.getPaymentTotalCost(any(Payment.class))).thenReturn(paidInCard + paidInCash);
        assertEquals(paidInCard + paidInCash, calculatingService.getOrderTotalCost(order));
    }
}