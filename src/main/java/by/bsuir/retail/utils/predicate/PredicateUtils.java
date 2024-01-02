package by.bsuir.retail.utils.predicate;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.sales.profitability.FilterType;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.utils.predicate.impl.OrderLinePredicateUtils;
import by.bsuir.retail.utils.predicate.impl.OrderPredicateUtils;
import by.bsuir.retail.utils.predicate.impl.SupplyLinePredicateUtils;
import by.bsuir.retail.utils.predicate.impl.SupplyPredicateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public interface PredicateUtils<T> {
    Predicate<T> between(LocalDateTime start, LocalDateTime end);
    Predicate<T> inCoffeeShop(long coffeeShopId);
    Class<T> getPredicateClass();
    default Predicate<T> inCoffeeShopBetween(long coffeeShopId, LocalDateTime start, LocalDateTime end) {
        return entity -> between(start, end).and(inCoffeeShop(coffeeShopId)).test(entity);
    }

    static <T> Predicate<T> empty(Class<T> tClass) {
        return obj -> true;
    }

    default Predicate<T> predicate(FinancialRequest request) {
        FilterType filterType = FilterType.fromType(request.getFilterType());
        if(filterType.equals(FilterType.NONE)) return empty(getPredicateClass());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = request.getStartTime() != null ? LocalDateTime.parse(request.getStartTime(), formatter) : null;
        LocalDateTime end = request.getEndTime() != null ? LocalDateTime.parse(request.getEndTime(), formatter) : null;
        return switch (filterType) {
            case BY_COFFEE_SHOP -> inCoffeeShop(request.getCoffeeShopId());
            case BY_DATE -> between(start, end);
            case BY_COFFEE_SHOP_AND_DATE -> inCoffeeShopBetween(request.getCoffeeShopId(), start, end);
            default -> throw new UnsupportedOperationException();
        };
    }

    static PredicateUtils<Order> forOrder() {
        return new OrderPredicateUtils();
    }
    static PredicateUtils<Supply> forSupply() {
        return new SupplyPredicateUtils();
    }
    static PredicateUtils<OrderLine> forOrderLine() {
        return new OrderLinePredicateUtils();
    }
    static PredicateUtils<SupplyLine> forSupplyLine() { return new SupplyLinePredicateUtils(); }
}
