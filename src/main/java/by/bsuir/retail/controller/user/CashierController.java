package by.bsuir.retail.controller.user;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.users.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cashier")
public class CashierController {
    private final CashierService cashierService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAll() {
        return cashierService.findAll();
    }

    @GetMapping("/coffeeShop/{coffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> getAllByCoffeeShop(@PathVariable long coffeeShopId) {
        return cashierService.findAllByCoffeeShop(coffeeShopId);
    }
}
