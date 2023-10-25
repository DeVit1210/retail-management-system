package by.bsuir.retail.dto.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
    private long id;
    private String name;
    private int weight;
    private double saleCost;
}
