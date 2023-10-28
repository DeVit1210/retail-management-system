package by.bsuir.retail.request.sales;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OrderAddingRequest {
    private long cashierId;
    private String createdAt;
    private Map<Long, Integer> orderComposition;
    private int discountPercent;
    private double paidInCash;
    private double paidWithCard;
}
