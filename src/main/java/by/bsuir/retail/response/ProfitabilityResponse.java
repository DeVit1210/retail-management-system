package by.bsuir.retail.response;


import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfitabilityResponse implements RetailManagementEntity {
    private String productName;
    private double saleCost;
    private double supplyCost;
    private double profitability;
}
