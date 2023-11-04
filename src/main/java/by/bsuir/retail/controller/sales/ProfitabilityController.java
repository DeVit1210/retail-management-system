package by.bsuir.retail.controller.sales;

import by.bsuir.retail.request.query.ProfitabilityRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.ProfitabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profitability")
public class ProfitabilityController {
    private final ProfitabilityService profitabilityService;
    @PostMapping
    public ResponseEntity<MultipleEntityResponse> calculateProfitability(@RequestBody ProfitabilityRequest request) {
        return profitabilityService.calculateProfitability(request);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<SingleEntityResponse> calculateProfitability(@PathVariable long productId,
                                                                       @RequestBody ProfitabilityRequest request) {
        return profitabilityService.calculateProfitability(productId, request);
    }
}
