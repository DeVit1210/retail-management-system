package by.bsuir.retail.request.sales;

import lombok.Getter;

import java.util.Map;

@Getter
public class OrderAddingRequest {
    private long cashierId;
    private String createdAt;
    private Map<Long, Integer> orderComposition;
    private int discountPercent;
    private double paidInCash;
    private double paidWithCard;
}
