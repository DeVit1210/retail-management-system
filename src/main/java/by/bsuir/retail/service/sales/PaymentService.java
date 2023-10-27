package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.repository.sales.PaymentRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment findById(long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Payment.class, paymentId));
    }

    public double getPaymentTotalCost(Payment payment) {
        return payment.getPaidWithCard() + payment.getPaidInCash();
    }
}
