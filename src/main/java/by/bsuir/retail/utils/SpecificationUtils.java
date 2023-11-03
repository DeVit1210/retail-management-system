package by.bsuir.retail.utils;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.entity.users.Cashier;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class SpecificationUtils {
    public static <T> Specification<T> empty() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<SupplyLine> materialsWithSupplyBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("purchasedAt"), start, end);
    }

    public static Specification<OrderLine> productsWithOrderBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("soldAt"), start, end);
    }

    public static Specification<SupplyLine> materialsWithSupplyInCoffeeShop(CoffeeShop coffeeShop) {
        return (root, query, criteriaBuilder) -> {
            Join<SupplyLine, Supply> supplyRoot = root.join("supply");
            return criteriaBuilder.equal(supplyRoot.get("coffee_shop_id"), coffeeShop.getId());
        };
    }

    public static Specification<OrderLine> productWithOrderInCoffeeShop(CoffeeShop coffeeShop) {
        return (root, query, criteriaBuilder) -> {
            Join<OrderLine, Order> orderRoot = root.join("orders");
            Join<Order, Cashier> cashierRoot = orderRoot.join("cashiers");
            return criteriaBuilder.equal(cashierRoot.get("coffee_shop_id"), coffeeShop.getId());
        };
    }

    public static Specification<SupplyLine> materialsInCoffeeShopBetween(CoffeeShop coffeeShop,
                                                                       LocalDateTime start,
                                                                       LocalDateTime end) {
        return Specification.where(materialsWithSupplyBetween(start, end)
                .and(materialsWithSupplyInCoffeeShop(coffeeShop)));
    }

    public static Specification<OrderLine> productsInCoffeeShopBetween(CoffeeShop coffeeShop,
                                                                     LocalDateTime start,
                                                                     LocalDateTime end) {
        return Specification.where(productsWithOrderBetween(start, end))
                .and(productWithOrderInCoffeeShop(coffeeShop));
    }
}
