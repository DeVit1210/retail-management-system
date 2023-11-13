package by.bsuir.retail.dto.supply;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransferDto implements RetailManagementEntity {
    private long id;
    private String toCoffeeShopName;
    private String fromCoffeeShopName;
    private String createdAt;
    private String materialName;
    private long quantity;
}
