package by.bsuir.retail.dto.supply;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplyLineDto implements RetailManagementEntity {
    private long id;
    private String materialName;
    private int quantity;
    private String purchasedAt;
    private double purchaseCost;
}
