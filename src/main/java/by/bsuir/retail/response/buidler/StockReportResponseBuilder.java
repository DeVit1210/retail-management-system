package by.bsuir.retail.response.buidler;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.response.report.StockReportResponse;

import java.time.LocalDate;

public class StockReportResponseBuilder {
    private final Material material;
    private String coffeeShopName;
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
        if(leftover == 0) return LocalDate.now();
        double averageDayExpense = (double) totalWeekExpense / 7;
        if(averageDayExpense == 0) return LocalDate.MAX;
        int daysRemained = (int) (leftover / averageDayExpense) + 1;
        return LocalDate.now().plusDays(daysRemained);
    }

    public StockReportResponse buildReport() {
        return StockReportResponse.builder()
                .coffeeShopName(coffeeShopName)
                .materialName(material.getName())
                .materialQuantity(leftover)
                .expirationDate(getExpirationDate())
                .build();
    }

    public StockReportResponseBuilder withCoffeeShopName(String name) {
        this.coffeeShopName = name;
        return this;
    }
}
