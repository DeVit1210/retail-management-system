package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class ProductTestBuilder implements TestBuilder<Product> {
    private long id = 1;
    private String name = "productName";
    private int weight = 100;
    private double saleCost = 1.0;
    private TechProcess techProcess = new TechProcess();
    private List<OrderLine> orderLines = new ArrayList<>();
    @Override
    public Product build() {
        return Product.builder()
                .name(name)
                .weight(weight)
                .techProcess(techProcess)
                .saleCost(saleCost)
                .salesHistory(orderLines)
                .build();
    }
}
