package by.bsuir.retail.dto.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShiftDto implements RetailManagementEntity {
    private long id;
    private String coffeeShopName;
    private String cashierName;
    private String startTime;
    private String endTime;
    private double totalIncome;
}
