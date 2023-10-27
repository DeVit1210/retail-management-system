package by.bsuir.retail.dto.supply;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class SupplyDto {
    private long id;
    private long coffeeShopId;
    private String coffeeShopName;
    private String supplierName;
    private Map<String, Integer> supplyComposition;
    private double totalCost;
}
