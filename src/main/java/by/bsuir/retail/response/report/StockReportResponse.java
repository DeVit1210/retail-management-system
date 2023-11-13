package by.bsuir.retail.response.report;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Builder
@ToString
public class StockReportResponse implements RetailManagementEntity {
    private String coffeeShopName;
    private String materialName;
    private int materialQuantity;
    private LocalDate expirationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockReportResponse that = (StockReportResponse) o;
        return Objects.equals(expirationDate, that.expirationDate);
    }

}
