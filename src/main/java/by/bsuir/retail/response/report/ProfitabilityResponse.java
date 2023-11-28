package by.bsuir.retail.response.report;


import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ProfitabilityResponse implements RetailManagementEntity {
    private String productName;
    private double saleCost;
    private double supplyCost;
    private double profitability;

    public ProfitabilityResponse(double saleCost, double supplyCost) {
        this.saleCost = saleCost;
        this.supplyCost = supplyCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitabilityResponse that = (ProfitabilityResponse) o;
        return Double.compare(that.supplyCost, supplyCost) == 0 &&
                Double.compare(that.saleCost, saleCost) == 0;
    }
}
