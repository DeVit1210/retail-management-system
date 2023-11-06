package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.response.StockReportResponse;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.buidler.StockReportResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.sales.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final ResponseBuilder responseBuilder;
    private final CoffeeShopService coffeeShopService;
    private final OrderLineService orderLineService;

    public ResponseEntity<MultipleEntityResponse> getStockReportForCoffeeShop(long coffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(coffeeShopId);
        List<OrderLine> orderLineList = orderLineService.getCoffeeShopOrderLineList(coffeeShop);
        Map<Material, Integer> totalExpensesForEachMaterial = this.getTotalExpensesForEachMaterial(orderLineList);
        Map<Material, Integer> warehouse = coffeeShop.getWarehouse();
        List<StockReportResponse> stockReportResponseList =
                totalExpensesForEachMaterial.entrySet().stream().map(toStockReportResponse(warehouse)).toList();
        return responseBuilder.buildMultipleEntityResponse(stockReportResponseList);
    }

    private Function<Map.Entry<Material, Integer>, StockReportResponse> toStockReportResponse(Map<Material, Integer> warehouse) {
        return entry -> StockReportResponseBuilder.forMaterial(entry.getKey())
                .withLeftover(warehouse.get(entry.getKey()))
                .withTotalExpense(entry.getValue())
                .buildReport();
    }

    private Map<Material, Integer> getAverageDayExpensesForEachMaterial(List<OrderLine> orderLineList) {
        final int daysQuantity = 7;
        Map<Material, Integer> totalExpensesForEachMaterial = this.getTotalExpensesForEachMaterial(orderLineList);
        return totalExpensesForEachMaterial.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() / daysQuantity));
    }

    private Map<Material, Integer> getTotalExpensesForEachMaterial(List<OrderLine> orderLineList) {
        return orderLineList.stream()
                .map(this::getExpensesForOrderLine)
                .reduce(new HashMap<>(), (result, current) -> {
                    result.replaceAll((material, quantity) -> quantity + current.getOrDefault(material, 0));
                    return result;
                });
    }

    private Map<Material, Integer> getExpensesForOrderLine(OrderLine orderLine) {
        Map<Material, Integer> ingredients = orderLine.getProduct().getTechProcess().getIngredients();
        return ingredients.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * orderLine.getQuantity()));
    }
}
