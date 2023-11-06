package by.bsuir.retail.utils.predicate.impl;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.utils.predicate.PredicateUtils;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class OrderPredicateUtils implements PredicateUtils<Order> {
    @Override
    public Predicate<Order> between(LocalDateTime start, LocalDateTime end) {
        return order -> order.getCreatedAt().isAfter(start)
                && order.getCreatedAt().isBefore(end);
    }

    @Override
    public Predicate<Order> inCoffeeShop(long coffeeShopId) {
        return order -> order.getCashier().getCoffeeShop().getId() == coffeeShopId;
    }

    @Override
    public Class<Order> getPredicateClass() {
        return Order.class;
    }

}
