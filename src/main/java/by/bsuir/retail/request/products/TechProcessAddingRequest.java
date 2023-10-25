package by.bsuir.retail.request.products;

import lombok.Getter;

import java.util.Map;

@Getter
public class TechProcessAddingRequest {
    private long productId;
    private Map<Long, Integer> processComposition;
}
