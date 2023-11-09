package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class OrderLineTestBuilder implements TestBuilder<OrderLine> {
    private Product product = null;
    private int quantity = 1;
    private LocalDateTime soldAt = LocalDateTime.now();
    private double saleCost = 1.0;
    @Override
    public OrderLine build() {
        return OrderLine.builder()
                .product(product)
                .quantity(quantity)
                .soldAt(soldAt)
                .saleCost(saleCost)
                .build();
    }
}
