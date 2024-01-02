package by.bsuir.retail.dto.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OrderDto implements RetailManagementEntity {
    private long id;
    private String cashierName;
    private String coffeeShopName;
    private String createdAt;
    private Map<String, Integer> orderComposition;
    private int discountPercent;
    private double totalCost;
}
