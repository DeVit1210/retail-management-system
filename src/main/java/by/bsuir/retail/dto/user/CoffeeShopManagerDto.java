package by.bsuir.retail.dto.user;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CoffeeShopManagerDto implements RetailManagementEntity {
    private long id;
    private String fullName;
    private String coffeeShopName;
}
