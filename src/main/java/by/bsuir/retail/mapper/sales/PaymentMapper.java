package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.PaymentDto;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "totalPaymentAmount", expression = "java(payment.getPaidInCash() + payment.getPaidWithCard())")
    PaymentDto toPaymentDto(Payment payment);
    @Mapping(target = "order", source = "order")
    @Mapping(target = "createdAt", source = "request.createdAt", dateFormat = "yyyy-MM-dd")
    Payment toPayment(OrderAddingRequest request, Order order);
    List<PaymentDto> toPaymentDtoList(List<Payment> paymentList);
}
