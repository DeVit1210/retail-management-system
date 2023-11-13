package by.bsuir.retail.controller.sales;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.sales.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatisticsController {
    private final ShiftService shiftService;
    @GetMapping("/active")
    public ResponseEntity<MultipleEntityResponse> getAllCurrentShiftsStatistics() {
        return shiftService.getAllActiveShiftsStatistics();
    }
    @GetMapping("/active/{coffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> getActiveCoffeeShopShiftStatistics(@PathVariable long coffeeShopId) {
        return shiftService.getCoffeeShopActiveShifts(coffeeShopId);
    }
    @GetMapping("/{coffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> getCoffeeShopSalesHistory(@PathVariable long coffeeShopId) {
        return shiftService.getCoffeeShopShiftHistory(coffeeShopId);
    }
}
