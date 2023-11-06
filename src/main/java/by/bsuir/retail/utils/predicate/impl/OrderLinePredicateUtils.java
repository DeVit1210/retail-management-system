package by.bsuir.retail.utils.predicate.impl;

import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.utils.predicate.PredicateUtils;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class OrderLinePredicateUtils implements PredicateUtils<OrderLine> {
    @Override
    public Predicate<OrderLine> between(LocalDateTime start, LocalDateTime end) {
        return orderLine -> orderLine.getSoldAt().isAfter(start)
                && orderLine.getSoldAt().isBefore(end);
    }

    @Override
    public Predicate<OrderLine> inCoffeeShop(long coffeeShopId) {
        return orderLine -> orderLine.getOrder().getCashier().getCoffeeShop().getId() == coffeeShopId;
    }

    @Override
    public Class<OrderLine> getPredicateClass() {
        return OrderLine.class;
    }
}
