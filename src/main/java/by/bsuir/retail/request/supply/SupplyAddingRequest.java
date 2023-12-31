package by.bsuir.retail.request.supply;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class SupplyAddingRequest {
    private long coffeeShopId;
    private long supplierId;
    private List<Long> materialIdList;
    private List<Integer> materialQuantityList;
    private List<Double> materialCostList;
}
