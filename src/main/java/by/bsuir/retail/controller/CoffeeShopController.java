package by.bsuir.retail.controller;


import by.bsuir.retail.request.CoffeeShopAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coffeeShop")
public class CoffeeShopController {
    private final CoffeeShopService coffeeShopService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAll() {
        return coffeeShopService.getAll();
    }

    @PostMapping
    public ResponseEntity<SingleEntityResponse> addCoffeeShop(@RequestBody CoffeeShopAddingRequest request) {
        return coffeeShopService.addCoffeeShop(request);
    }
}
