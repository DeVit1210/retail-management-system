package by.bsuir.retail.dto.user;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CashierDto implements RetailManagementEntity {
    private long id;
    private String fullName;
    private long coffeeShopId;
    private String coffeeShopName;
    private int shiftQuantity;
}
