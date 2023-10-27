package by.bsuir.retail.dto.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderLineDto implements RetailManagementEntity {
    private long id;
    private String productName;
    private int quantity;
    private String soldAt;
    private double purchaseCost;
}
