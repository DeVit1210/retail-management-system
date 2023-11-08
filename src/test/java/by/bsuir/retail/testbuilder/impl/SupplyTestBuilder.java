package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class SupplyTestBuilder implements TestBuilder<Supply> {
    private CoffeeShop coffeeShop = CoffeeShopTestBuilder.builder().build();
    private Supplier supplier = SupplierTestBuilder.builder().build();
    private List<SupplyLine> composition = new ArrayList<>();
    @Override
    public Supply build() {
        return Supply.builder()
                .coffeeShop(coffeeShop)
                .supplier(supplier)
                .composition(composition)
                .build();
    }
}
