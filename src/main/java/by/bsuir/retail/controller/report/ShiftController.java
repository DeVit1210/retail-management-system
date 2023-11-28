package by.bsuir.retail.controller.report;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.sales.ShiftService;
import by.bsuir.retail.service.users.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shift")
public class ShiftController {
    private final ShiftService shiftService;
    private final CashierService cashierService;

    @PutMapping("/open")
    public void openShift() {
        shiftService.openShift(cashierService.getAuthenticatedCashier());
    }

    @PutMapping("/close")
    public ResponseEntity<SingleEntityResponse> closeShift() {
        return shiftService.closeShift(cashierService.getAuthenticatedCashier());
    }

    @GetMapping("/active/cashier")
    public ResponseEntity<MultipleEntityResponse> getCurrentShiftHistory() {
        return shiftService.getCurrentShiftHistory(cashierService.getAuthenticatedCashier());
    }

    @GetMapping("/history/cashier")
    public ResponseEntity<MultipleEntityResponse> getShiftHistory() {
        return shiftService.getShiftHistory(cashierService.getAuthenticatedCashier());
    }

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

    @GetMapping("/history")
    public ResponseEntity<MultipleEntityResponse> getAll() {
        return shiftService.getAll();
    }
}
