package by.bsuir.retail.dto.sales;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentDto {
    private long id;
    private String createdAt;
    private double paidInCash;
    private double paidWithCard;
    private double totalPaymentAmount;
}
