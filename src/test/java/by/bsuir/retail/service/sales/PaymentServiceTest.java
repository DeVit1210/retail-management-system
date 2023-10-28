package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.testbuilder.impl.PaymentTestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;
    @Value("${test.payment.cash}")
    private double paidInCash;
    @Value("${test.payment.card}")
    private double paidInCard;
    @Test
    void testCalculateTotalCost() {
        Payment payment = PaymentTestBuilder.builder().withPaidInCard(paidInCard).withPaidInCash(paidInCash).build();
        assertEquals(paidInCard + paidInCash, paymentService.getPaymentTotalCost(payment));
    }
}