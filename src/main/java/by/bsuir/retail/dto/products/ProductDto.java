package by.bsuir.retail.dto.products;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto implements RetailManagementEntity {
    private long id;
    private String name;
    private int weight;
    private double saleCost;
}
