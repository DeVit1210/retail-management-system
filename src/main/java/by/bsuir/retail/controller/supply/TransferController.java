package by.bsuir.retail.controller.supply;

import by.bsuir.retail.request.supply.TransferAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.supply.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {
    private final TransferService transferService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAll() {
        return transferService.getAll();
    }

    @GetMapping("/from/{coffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> getAllFromCoffeeShop(@PathVariable long coffeeShopId) {
        return transferService.getAllFrom(coffeeShopId);
    }

    @GetMapping("/to/{coffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> getAllToCoffeeShop(@PathVariable long coffeeShopId) {
        return transferService.getAllTo(coffeeShopId);
    }

    @GetMapping("/from/{fromCoffeeShopId}/to/{toCoffeeShopId}")
    public ResponseEntity<MultipleEntityResponse> getAllBetween(@PathVariable long fromCoffeeShopId,
                                                                @PathVariable long toCoffeeShopId) {
        return transferService.getAllBetween(fromCoffeeShopId, toCoffeeShopId);
    }

    @PostMapping
    public ResponseEntity<SingleEntityResponse> addTransfer(@RequestBody TransferAddingRequest request) {
        return transferService.createTransfer(request);
    }

}
