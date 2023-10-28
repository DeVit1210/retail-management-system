package by.bsuir.retail.request.supply;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SupplyLineAddingRequest {
    private long materialId;
    private int quantity;
    private double purchaseCost;
}
