package by.bsuir.retail.utils.predicate.impl;

import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.utils.predicate.PredicateUtils;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class SupplyPredicateUtils implements PredicateUtils<Supply> {
    @Override
    public Predicate<Supply> between(LocalDateTime start, LocalDateTime end) {
        return supply -> supply.getCreatedAt().isAfter(start)
                && supply.getCreatedAt().isBefore(end);
    }

    @Override
    public Predicate<Supply> inCoffeeShop(long coffeeShopId) {
        return supply -> supply.getCoffeeShop().getId() == coffeeShopId;
    }

    @Override
    public Class<Supply> getPredicateClass() {
        return Supply.class;
    }
}
