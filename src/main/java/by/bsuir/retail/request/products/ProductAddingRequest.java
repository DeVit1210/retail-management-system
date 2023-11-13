package by.bsuir.retail.request.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductAddingRequest {
    private String name;
    private int weight;
    private double saleCost;
}
