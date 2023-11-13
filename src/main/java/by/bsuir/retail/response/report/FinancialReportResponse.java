package by.bsuir.retail.response.report;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FinancialReportResponse implements RetailManagementEntity {
    private long orderQuantity;
    private double totalIncome;
    private long supplyQuantity;
    private double totalExpenses;
    private double clearIncome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialReportResponse that = (FinancialReportResponse) o;
        return orderQuantity == that.orderQuantity &&
                Double.compare(that.totalIncome, totalIncome) == 0 &&
                supplyQuantity == that.supplyQuantity &&
                Double.compare(that.totalExpenses, totalExpenses) == 0;
    }

}
