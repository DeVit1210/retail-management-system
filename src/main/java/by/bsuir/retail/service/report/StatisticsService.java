package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.response.report.FinancialReportResponse;
import by.bsuir.retail.response.buidler.FinancialReportResponseBuilder;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.sales.OrderService;
import by.bsuir.retail.service.supply.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final OrderService orderService;
    private final SupplyService supplyService;
    private final ResponseBuilder responseBuilder;

    public ResponseEntity<SingleEntityResponse> generateFinancialReport(FinancialRequest request) {
        FinancialReportResponse financialReportResponse = FinancialReportResponseBuilder.forRequest(request)
                .withOrderListProvider(orderService::findAll)
                .andOrderIncomeMapper(orderService::getOrderTotalCost)
                .withSupplyListProvider(supplyService::findAll)
                .andSupplyTotalCostMapper(Supply::getTotalCost)
                .buildReport();

        return responseBuilder.buildSingleEntityResponse(financialReportResponse);
    }
}
