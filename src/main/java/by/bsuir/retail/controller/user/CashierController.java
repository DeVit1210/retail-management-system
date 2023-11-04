package by.bsuir.retail.controller.user;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.sales.ShiftService;
import by.bsuir.retail.service.users.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cashier")
public class CashierController {
    private final ShiftService shiftService;
    private final CashierService cashierService;
    @PutMapping("/shift/open")
    public void openShift() {
        shiftService.openShift(cashierService.getAuthenticatedCashier());
    }
    @PutMapping("/shift/close")
    public ResponseEntity<SingleEntityResponse> closeShift() {
        return shiftService.closeShift(cashierService.getAuthenticatedCashier());
    }
    @GetMapping("/shift/current")
    public ResponseEntity<MultipleEntityResponse> getCurrentShiftHistory() {
        return shiftService.getCurrentShiftHistory(cashierService.getAuthenticatedCashier());
    }
    @GetMapping("/shift")
    public ResponseEntity<MultipleEntityResponse> getShiftHistory() {
        return shiftService.getShiftHistory(cashierService.getAuthenticatedCashier());
    }

}
