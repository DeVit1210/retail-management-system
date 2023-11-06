package by.bsuir.retail.response.buidler;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.response.StockReportResponse;

import java.time.LocalDate;

public class StockReportResponseBuilder {
    private final Material material;
    private int leftover;
    private int totalWeekExpense;

    private StockReportResponseBuilder(Material material) {
        this.material = material;
    }
    public static StockReportResponseBuilder forMaterial(Material material) {
        return new StockReportResponseBuilder(material);
    }

    public StockReportResponseBuilder withLeftover(int leftover) {
        this.leftover = leftover;
        return this;
    }

    public StockReportResponseBuilder withTotalExpense(int totalExpense) {
        this.totalWeekExpense = totalExpense;
        return this;
    }

    private LocalDate getExpirationDate() {
        int averageDayExpense = totalWeekExpense / 7;
        int daysRemained = (leftover / averageDayExpense) + 1;
        return LocalDate.now().plusDays(daysRemained);
    }

    public StockReportResponse buildReport() {
        return StockReportResponse.builder()
                .materialName(material.getName())
                .materialQuantity(leftover)
                .expirationDate(getExpirationDate())
                .build();
    }
}
