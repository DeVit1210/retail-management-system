package by.bsuir.retail.entity.sales.profitability;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum FilterType {

    BY_COFFEE_SHOP("coffeeShop"),
    BY_DATE("date"),
    BY_COFFEE_SHOP_AND_DATE("coffeeShop date"),
    NONE("none");

    private final String type;

    public static FilterType fromType(String type) {
        return Arrays.stream(values())
                .filter(profitabilityFilterType -> profitabilityFilterType.getType().equals(type))
                .findFirst()
                .orElseThrow();
    }
}
