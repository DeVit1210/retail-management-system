package by.bsuir.retail.controller.report;


import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.report.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report/financial")
public class FinancialReportController {
    private final StatisticsService statisticsService;

    @PostMapping
    public ResponseEntity<SingleEntityResponse> generateFinancialReport(@RequestBody FinancialRequest request) {
        return statisticsService.generateFinancialReport(request);
    }
}
