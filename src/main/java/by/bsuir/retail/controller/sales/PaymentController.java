package by.bsuir.retail.controller.sales;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.sales.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentController;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllPayment() {
        return paymentController.findAll();
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<SingleEntityResponse> getPayment(@PathVariable long paymentId) {
        return paymentController.getById(paymentId);
    }
}