package by.bsuir.retail.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CashierDto {
    private long id;
    private String cashierName;
    private long coffeeShopId;
    private long coffeeShopName;
    private int orderQuantity;
}
