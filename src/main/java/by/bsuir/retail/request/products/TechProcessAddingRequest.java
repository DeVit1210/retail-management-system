package by.bsuir.retail.request.products;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class TechProcessAddingRequest {
    private long productId;
    private Map<Long, Integer> processComposition;
}
