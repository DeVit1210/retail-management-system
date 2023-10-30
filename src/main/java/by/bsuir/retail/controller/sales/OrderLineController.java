package by.bsuir.retail.controller.sales;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.sales.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderLine")
public class OrderLineController {
    private final OrderLineService orderLineController;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllOrderLine() {
        return orderLineController.findAll();
    }
    @GetMapping("/{orderLineId}")
    public ResponseEntity<SingleEntityResponse> getOrderLine(@PathVariable long orderLineId) {
        return orderLineController.getById(orderLineId);
    }
}
