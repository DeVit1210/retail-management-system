package by.bsuir.retail.dto.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentDto implements RetailManagementEntity {
    private long id;
    private String createdAt;
    private double paidInCash;
    private double paidWithCard;
    private double totalPaymentAmount;
}
