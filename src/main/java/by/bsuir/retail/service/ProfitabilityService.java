package by.bsuir.retail.service;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.service.supply.SupplyLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfitabilityService {
    private final SupplyLineService supplyLineService;
    public double calculateProfitability(TechProcess techProcess, List<OrderLine> orderLineList) {
        Map<Material, Integer> ingredients = techProcess.getIngredients();
        double averageSaleCost = getAverageSaleCost(orderLineList);
        double averagePurchaseCost = composeIngredientsAverageCost(ingredients);
        return calculateProfitability(averageSaleCost, averagePurchaseCost);
    }

    private double composeIngredientsAverageCost(Map<Material, Integer> ingredients) {
        return ingredients.entrySet().stream()
                .map(entry -> {
                    List<SupplyLine> supplyLineList = supplyLineService.findAllByMaterial(entry.getKey());
                    return getAverageSupplyCost(supplyLineList, entry.getValue());
                })
                .reduce(0.0, Double::sum);
    }

    private double calculateProfitability(double averageSaleCost, double averagePurchaseCost) {
        return (averageSaleCost - averagePurchaseCost) / averagePurchaseCost;
    }

    private double getAverageSupplyCost(List<SupplyLine> supplyLineList, int quantity) {
        double totalPurchaseCost = supplyLineList.stream()
                .mapToDouble(SupplyLine::getPurchaseCost)
                .reduce(0.0, Double::sum);
        return (totalPurchaseCost / supplyLineList.size()) * quantity;
    }
    
    private double getAverageSaleCost(List<OrderLine> orderLineList) {
        double totalSaleCost = orderLineList.stream()
                .mapToDouble(OrderLine::getSaleCost)
                .reduce(0.0, Double::sum);
        return totalSaleCost / orderLineList.size();
    }
}
