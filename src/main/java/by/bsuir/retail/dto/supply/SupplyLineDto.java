package by.bsuir.retail.dto.supply;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplyLineDto {
    private long id;
    private String materialName;
    private int materialQuantity;
    private String purchasedAt;
    private double purchaseCost;
}
