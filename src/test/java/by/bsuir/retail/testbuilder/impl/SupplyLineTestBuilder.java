package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.testbuilder.TestBuilder;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class SupplyLineTestBuilder implements TestBuilder<SupplyLine> {
    private Material material = Material.builder().build();
    private Supply supply = null;
    private LocalDateTime purchasedAt = LocalDateTime.now();
    private double purchaseCost = 1.0;
    private int quantity = 1;
    @Override
    public SupplyLine build() {
        return SupplyLine.builder()
                .material(material)
                .supply(supply)
                .purchaseCost(purchaseCost)
                .purchasedAt(purchasedAt)
                .quantity(quantity)
                .build();
    }
}
