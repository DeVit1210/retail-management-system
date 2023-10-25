package by.bsuir.retail.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CoffeeShopManagerDto {
    private long id;
    private String fullName;
    private String coffeeShopName;
}
