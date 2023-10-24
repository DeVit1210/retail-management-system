package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class OrderTestBuilder implements TestBuilder<Order> {
    private Cashier cashier = CashierTestBuilder.builder().build();
    private LocalDateTime createdAt = LocalDateTime.now();
    private Map<Product, Integer> composition = new HashMap<>();
    private int discountPercent = 0;
    private Payment payment = PaymentTestBuilder.builder().build();
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
