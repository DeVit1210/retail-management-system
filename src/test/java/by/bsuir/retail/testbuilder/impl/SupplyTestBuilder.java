package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
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
public class SupplyTestBuilder implements TestBuilder<Supply> {
    private CoffeeShop coffeeShop = CoffeeShopTestBuilder.builder().build();
    private Supplier supplier = SupplierTestBuilder.builder().build();
    private List<SupplyLine> composition = new ArrayList<>();
    private double totalCost = 1000;
    private LocalDateTime createdAt = LocalDateTime.now();
    @Override
    public Supply build() {
        return Supply.builder()
                .coffeeShop(coffeeShop)
                .supplier(supplier)
                .composition(composition)
                .totalCost(totalCost)
                .createdAt(createdAt)
                .build();
    }
}
