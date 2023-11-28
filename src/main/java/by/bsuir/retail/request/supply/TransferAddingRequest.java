package by.bsuir.retail.request.supply;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransferAddingRequest {
    private long fromCoffeeShopId;
    private long toCoffeeShopId;
    private long materialId;
    private int quantity;
}
