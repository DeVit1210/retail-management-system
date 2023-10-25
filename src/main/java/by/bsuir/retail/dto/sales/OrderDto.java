package by.bsuir.retail.dto.sales;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OrderDto {
    private long id;
    private String cashierName;
    private String createdAt;
    private Map<String, Integer> orderComposition;
    private int discountPercent;
    private double totalCost;
//    private int totalCostWithDiscount;
//    private int totalCostWithoutDiscount;
}
