package by.bsuir.retail.request.query;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FinancialRequest {
    private long coffeeShopId;
    private String startTime;
    private String endTime;
    private String filterType;
}
