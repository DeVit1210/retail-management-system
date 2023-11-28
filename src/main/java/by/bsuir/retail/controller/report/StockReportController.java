package by.bsuir.retail.controller.report;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.report.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report/stock")
public class StockReportController {
    private final StockService stockService;

    @GetMapping("/coffeeShop/{coffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> generateStockResponseForCoffeeShop(@PathVariable long coffeeShopId) {
        return stockService.generateStockReportForCoffeeShop(coffeeShopId);
    }

    @GetMapping("/material/{materialId}")
    public ResponseEntity<MultipleEntityResponse> generateStockResponseForMaterial(@PathVariable long materialId) {
        return stockService.generateStockReportForMaterial(materialId);
    }
}
