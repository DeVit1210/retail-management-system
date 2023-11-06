package by.bsuir.retail.response;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StockReportResponse implements RetailManagementEntity {
    private String materialName;
    private int materialQuantity;
    private LocalDate expirationDate;
}
