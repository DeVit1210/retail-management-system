package by.bsuir.retail.response.buidler;


import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.response.report.ProfitabilityResponse;

public class ProfitabilityResponseBuilder {
    private final String productName;
    private double averageSaleCost;
    private double averagePurchaseCost;

    private ProfitabilityResponseBuilder(Product product) {
        this.productName = product.getName();
    }
    public static ProfitabilityResponseBuilder withProduct(Product product) {
        return new ProfitabilityResponseBuilder(product);
    }

    public ProfitabilityResponseBuilder withSaleCost(double averageSaleCost) {
        this.averageSaleCost = averageSaleCost;
        return this;
    }

    public ProfitabilityResponseBuilder withPurchaseCost(double averagePurchaseCost) {
        this.averagePurchaseCost = averagePurchaseCost;
        return this;
    }

    private double calculateProfitability() {
        if(averagePurchaseCost == 0 || averageSaleCost == 0) return 0;
        return (averageSaleCost - averagePurchaseCost) / averageSaleCost * 100;
    }

    public ProfitabilityResponse createResponse() {
        return new ProfitabilityResponse(productName, averageSaleCost, averagePurchaseCost, calculateProfitability());
    }
}
