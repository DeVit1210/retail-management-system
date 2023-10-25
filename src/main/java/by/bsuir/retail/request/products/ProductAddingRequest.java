package by.bsuir.retail.request.products;

import lombok.Getter;

@Getter
public class ProductAddingRequest {
    private String name;
    private int weight;
    private double saleCost;
}
