package by.bsuir.retail.dto.supply;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class SupplyDto implements RetailManagementEntity {
    private long id;
    private long coffeeShopId;
    private String coffeeShopName;
    private String supplierName;
    private String createdAt;
    private Map<String, Integer> supplyComposition;
    private double totalCost;
}
