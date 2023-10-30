package by.bsuir.retail.controller.sales;

import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.sales.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllOrder() {
        return orderService.findAll();
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<SingleEntityResponse> getOrder(@PathVariable long orderId) {
        return orderService.getById(orderId);
    }
    @PostMapping
    public ResponseEntity<SingleEntityResponse> addOrder(@RequestBody OrderAddingRequest request) {
        return orderService.addOrder(request);
    }
}