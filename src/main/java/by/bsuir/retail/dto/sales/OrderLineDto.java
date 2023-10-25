package by.bsuir.retail.dto.sales;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderLineDto {
    private long id;
    private String productName;
    private int quantity;
    private String soldAt;
    private double purchaseCost;
}
