package by.bsuir.retail.response.report;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FinancialReportResponse implements RetailManagementEntity {
    private long orderQuantity;
    private double totalIncome;
    private long supplyQuantity;
    private double totalExpenses;
    private double clearIncome;
}