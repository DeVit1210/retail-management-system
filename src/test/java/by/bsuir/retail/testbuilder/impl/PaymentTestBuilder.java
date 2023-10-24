package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class PaymentTestBuilder implements TestBuilder<Payment> {
    private Order order = OrderTestBuilder.builder().build();
    private LocalDateTime createdAt = order.getCreatedAt();
    private double paidInCash = 1.0;
    private double paidInCard = 1.0;
    @Override
    public Payment build() {
        return Payment.builder()
                .order(order)
                .createdAt(createdAt)
                .paidInCash(paidInCash)
                .paidWithCard(paidInCard)
                .build();
    }
}
