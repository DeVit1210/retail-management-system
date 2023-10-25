package by.bsuir.retail.request.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MaterialAddingRequest {
    private String name;
    private int weight;
    private double purchaseCost;
}
