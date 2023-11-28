package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.mapper.sales.PaymentMapper;
import by.bsuir.retail.repository.sales.PaymentRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final SpecificationService specificationService;
    private final PaymentRepository paymentRepository;
    private final ResponseBuilder responseBuilder;
    private final PaymentMapper mapper;
    public Payment findById(long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Payment.class, paymentId));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Payment> paymentList = paymentRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toPaymentDtoList(paymentList));
    }

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<Payment> specificationChain =
                specificationService.createSpecificationChain(request, Payment.class);
        List<Payment> paymentList = paymentRepository.findAll(specificationChain);
        return responseBuilder.buildMultipleEntityResponse(mapper.toPaymentDtoList(paymentList));
    }

    public double getPaymentTotalCost(Payment payment) {
        return payment.getPaidWithCard() + payment.getPaidInCash();
    }

    public void createPayment(OrderAddingRequest request, Order order) {
        Payment payment = mapper.toPayment(request, order);
        paymentRepository.save(payment);
    }

    public ResponseEntity<SingleEntityResponse> getById(long paymentId) {
        return responseBuilder.buildSingleEntityResponse(findById(paymentId));
    }

    public Payment findByOrder(Order order) {
        return paymentRepository.findByOrder(order);
    }
}
