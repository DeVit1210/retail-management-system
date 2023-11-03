package by.bsuir.retail.entity.sales.profitability;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProfitabilityFilterType {

    BY_COFFEE_SHOP("coffeeShop"),
    BY_DATE("date"),
    BY_COFFEE_SHOP_AND_DATE("coffeeShop date");

    private final String type;

    public static ProfitabilityFilterType fromType(String type) {
        return Arrays.stream(values())
                .filter(profitabilityFilterType -> profitabilityFilterType.getType().equals(type))
                .findFirst()
                .orElseThrow();
    }
}
