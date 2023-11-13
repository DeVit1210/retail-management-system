package by.bsuir.retail.response.buidler;


import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.response.report.FinancialReportResponse;
import by.bsuir.retail.utils.predicate.PredicateUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

public class FinancialReportResponseBuilder {
    private final FinancialRequest request;
    private List<Order> orderStream;
    private List<Supply> supplyStream;
    private double totalIncome;
    private double totalExpense;

    private FinancialReportResponseBuilder(FinancialRequest request) {
        this.request = request;
    }

    public static FinancialReportResponseBuilder forRequest(FinancialRequest request) {
        return new FinancialReportResponseBuilder(request);
    }
    public FinancialReportResponseBuilder withOrderListProvider(Supplier<List<Order>> orderListProvider) {
        this.orderStream = orderListProvider.get().stream().filter(PredicateUtils.forOrder().predicate(request)).toList();
        return this;
    }

    public FinancialReportResponseBuilder andOrderIncomeMapper(ToDoubleFunction<? super Order> incomeMapper) {
        this.totalIncome = orderStream.stream().mapToDouble(incomeMapper).reduce(0.0, Double::sum);
        return this;
    }

    public FinancialReportResponseBuilder withSupplyListProvider(Supplier<List<Supply>> supplyListProvider) {
        this.supplyStream = supplyListProvider.get().stream().filter(PredicateUtils.forSupply().predicate(request)).toList();
        return this;
    }

    public FinancialReportResponseBuilder andSupplyTotalCostMapper(ToDoubleFunction<? super Supply> totalCostMapper) {
        this.totalExpense = supplyStream.stream().mapToDouble(totalCostMapper).reduce(0.0, Double::sum);
        return this;
    }

    public FinancialReportResponse buildReport() {
        return FinancialReportResponse.builder()
                .orderQuantity(orderStream.size())
                .supplyQuantity(supplyStream.size())
                .totalIncome(totalIncome)
                .totalExpenses(totalExpense)
                .clearIncome(totalIncome - totalExpense)
                .build();
    }
}