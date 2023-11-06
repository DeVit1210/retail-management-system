package by.bsuir.retail.utils.predicate.impl;

import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.utils.predicate.PredicateUtils;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class SupplyLinePredicateUtils implements PredicateUtils<SupplyLine> {
    @Override
    public Predicate<SupplyLine> between(LocalDateTime start, LocalDateTime end) {
        return supplyLine -> supplyLine.getPurchasedAt().isAfter(start)
                && supplyLine.getPurchasedAt().isBefore(end);
    }

    @Override
    public Predicate<SupplyLine> inCoffeeShop(long coffeeShopId) {
        return supplyLine -> supplyLine.getSupply().getCoffeeShop().getId() == coffeeShopId;
    }

    @Override
    public Class<SupplyLine> getPredicateClass() {
        return SupplyLine.class;
    }
}
