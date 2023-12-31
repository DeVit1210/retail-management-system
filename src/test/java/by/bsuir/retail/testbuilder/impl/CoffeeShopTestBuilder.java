package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.CoffeeShopManager;
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
public class CoffeeShopTestBuilder implements TestBuilder<CoffeeShop> {
    private long id = 1;
    private String name = "coffeeShopName";
    private String address = "coffeeShopAddress";
    private List<Supply> supplyList = new ArrayList<>();
    private List<Cashier> cashierList = new ArrayList<>();
    private Map<Material, Integer> warehouse = new HashMap<>();
    @Override
    public CoffeeShop build() {
        return CoffeeShop.builder()
                .id(id)
                .name(name)
                .address(address)
                .supplyList(supplyList)
                .cashierList(cashierList)
                .warehouse(warehouse)
                .build();
    }
}
