package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.response.report.StockReportResponse;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.buidler.StockReportResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.products.MaterialService;
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
    private final MaterialService materialService;

    public ResponseEntity<MultipleEntityResponse> generateStockReportForCoffeeShop(long coffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(coffeeShopId);
        List<OrderLine> orderLineList = orderLineService.getCoffeeShopOrderLineList(coffeeShop);
        Map<Material, Integer> totalExpensesForEachMaterial = this.getTotalExpensesForEachMaterial(orderLineList);
        List<StockReportResponse> stockReportResponseList =
                totalExpensesForEachMaterial.entrySet().stream().map(toStockReportResponse(coffeeShop)).toList();
        return responseBuilder.buildMultipleEntityResponse(stockReportResponseList);
    }

    public ResponseEntity<MultipleEntityResponse> generateStockReportForMaterial(long materialId) {
        Material material = materialService.findById(materialId);
        Map<CoffeeShop, Integer> totalExpensesForEachCoffeeShop = getTotalExpensesForEachCoffeeShop(material);
        List<StockReportResponse> stockReportResponseList =
                totalExpensesForEachCoffeeShop.entrySet().stream().map(toStockReportResponse(material)).toList();
        return responseBuilder.buildMultipleEntityResponse(stockReportResponseList);
    }

    private Function<Map.Entry<Material, Integer>, StockReportResponse> toStockReportResponse(CoffeeShop coffeeShop) {
        return entry -> StockReportResponseBuilder.forMaterial(entry.getKey())
                .withCoffeeShopName(coffeeShop.getName())
                .withLeftover(coffeeShop.getWarehouse().get(entry.getKey()))
                .withTotalExpense(entry.getValue())
                .buildReport();
    }

    private Function<Map.Entry<CoffeeShop, Integer>, StockReportResponse> toStockReportResponse(Material material) {
        return entry -> StockReportResponseBuilder.forMaterial(material)
                .withCoffeeShopName(entry.getKey().getName())
                .withLeftover(entry.getKey().getWarehouse().get(material))
                .withTotalExpense(entry.getValue())
                .buildReport();
    }

    private Map<Material, Integer> getTotalExpensesForEachMaterial(List<OrderLine> orderLineList) {
        return orderLineList.stream()
                .map(this::getExpensesForOrderLine)
                .reduce(new HashMap<>(), StockService::updateResultingMap);
    }

    private static Map<Material, Integer> updateResultingMap(Map<Material, Integer> result, Map<Material, Integer> current) {
        current.forEach(((material, quantity) -> {
            if(result.containsKey(material)) {
                result.replace(material, result.get(material) + quantity);
            } else result.put(material, quantity);
        }));
        return result;
    }


    private Map<Material, Integer> getExpensesForOrderLine(OrderLine orderLine) {
        Map<Material, Integer> ingredients = orderLine.getProduct().getTechProcess().getIngredients();
        return ingredients.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * orderLine.getQuantity()));
    }

    private Map<CoffeeShop, List<OrderLine>> groupedOrderLinesByCoffeeShop() {
        return orderLineService.findAll().stream()
                .collect(Collectors.groupingBy(orderLine -> orderLine.getOrder().getCashier().getCoffeeShop()));
    }

    private Map<CoffeeShop, Integer> getTotalExpensesForEachCoffeeShop(Material material) {
        Map<CoffeeShop, List<OrderLine>> coffeeShopWithOrderLineList = this.groupedOrderLinesByCoffeeShop();
        return coffeeShopWithOrderLineList.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> this.getTotalExpensesForOneMaterial(material, entry.getValue())
                ));
    }

    private int getTotalExpensesForOneMaterial(Material material, List<OrderLine> orderLineList) {
        return orderLineList.stream()
                .mapToInt(orderLine -> this.getExpensesForMaterialInOrderLine(material, orderLine))
                .reduce(0, Integer::sum);
    }

    private int getExpensesForMaterialInOrderLine(Material material, OrderLine orderLine) {
        return getIngredients(orderLine).getOrDefault(material, 0) * orderLine.getQuantity();
    }

    private Map<Material, Integer> getIngredients(OrderLine orderLine) {
        return orderLine.getProduct().getTechProcess().getIngredients();
    }
}
