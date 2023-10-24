package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class MaterialTestBuilder implements TestBuilder<Material> {
    private String name = "materialName";
    private int weight = 1000;
    private double purchaseCost = 1.0;
    private List<SupplyLine> supplyHistory = new ArrayList<>();
    @Override
    public Material build() {
        return Material.builder()
                .name(name)
                .weight(weight)
                .purchaseCost(purchaseCost)
                .supplyHistory(supplyHistory)
                .build();
    }
}
