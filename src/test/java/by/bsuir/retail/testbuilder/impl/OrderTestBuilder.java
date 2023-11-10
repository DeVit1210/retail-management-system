package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class OrderTestBuilder implements TestBuilder<Order> {
    private Cashier cashier = CashierTestBuilder.builder().build();
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<OrderLine> composition = new ArrayList<>();
    private int discountPercent = 0;
    private Payment payment = null;
    @Override
    public Order build() {
        return Order.builder()
                .cashier(cashier)
                .createdAt(createdAt)
                .composition(composition)
                .discountPercent(discountPercent)
                .payment(payment)
                .build();
    }
}
