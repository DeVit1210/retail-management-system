package by.bsuir.retail.utils;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.sales.profitability.ProfitabilityFilterType;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.query.ProfitabilityRequest;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.function.Predicate;

public class PredicateUtils {
    private static Predicate<OrderLine> soldBetween(LocalDateTime start, LocalDateTime end) {
        return orderLine -> orderLine.getSoldAt().isAfter(start)
                && orderLine.getSoldAt().isBefore(end);
    }

    private static Predicate<SupplyLine> purchasedBetween(LocalDateTime start, LocalDateTime end) {
        return supplyLine -> supplyLine.getPurchasedAt().isAfter(start) &&
                supplyLine.getPurchasedAt().isBefore(end);
    }

    private static Predicate<OrderLine> soldInCoffeeShop(long coffeeShopId) {
        return orderLine -> orderLine.getOrder().getCashier().getCoffeeShop().getId() == coffeeShopId;
    }

    private static Predicate<SupplyLine> suppliedInCoffeeShop(long coffeeShopId) {
        return supplyLine -> supplyLine.getSupply().getCoffeeShop().getId() == coffeeShopId;
    }

    private static Predicate<OrderLine> soldInCoffeeShopBetween(long coffeeShopId,
                                                               LocalDateTime start,
                                                               LocalDateTime end) {
        return orderLine -> soldBetween(start,end).and(soldInCoffeeShop(coffeeShopId)).test(orderLine);
    }

    private static Predicate<SupplyLine> suppliedInCoffeeShopAndBetween(long coffeeShopId,
                                                                       LocalDateTime start,
                                                                       LocalDateTime end) {
        return supplyLine -> purchasedBetween(start, end).and(suppliedInCoffeeShop(coffeeShopId)).test(supplyLine);
    }

    public static <T> Predicate<T> empty(Class<T> tClass) {
        return obj -> true;
    }

    public static Predicate<SupplyLine> supplyLinePredicate(ProfitabilityRequest request) {
        if(request == null) return empty(SupplyLine.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(request.getStartTime(), formatter);
        LocalDateTime end = LocalDateTime.parse(request.getEndTime(), formatter);
        ProfitabilityFilterType filterType = ProfitabilityFilterType.fromType(request.getFilterType());
        return switch (filterType) {
            case BY_COFFEE_SHOP -> suppliedInCoffeeShop(request.getCoffeeShopId());
            case BY_DATE -> purchasedBetween(start, end);
            case BY_COFFEE_SHOP_AND_DATE -> suppliedInCoffeeShopAndBetween(request.getCoffeeShopId(), start, end);
        };
    }

    public static Predicate<OrderLine> orderLinePredicate(ProfitabilityRequest request) {
        if(request == null) return empty(OrderLine.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDateTime.parse(request.getStartTime(), formatter);
        LocalDateTime end = LocalDateTime.parse(request.getEndTime(), formatter);
        ProfitabilityFilterType filterType = ProfitabilityFilterType.fromType(request.getFilterType());
        return switch (filterType) {
            case BY_COFFEE_SHOP -> soldInCoffeeShop(request.getCoffeeShopId());
            case BY_DATE -> soldBetween(start, end);
            case BY_COFFEE_SHOP_AND_DATE -> soldInCoffeeShopBetween(request.getCoffeeShopId(), start, end);
        };
    }
}
