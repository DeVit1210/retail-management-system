package by.bsuir.retail.service.report;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.query.FinancialRequest;
import by.bsuir.retail.response.report.ProfitabilityResponse;
import by.bsuir.retail.response.buidler.ProfitabilityResponseBuilder;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.products.ProductService;
import by.bsuir.retail.service.supply.SupplyLineService;
import by.bsuir.retail.utils.predicate.PredicateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfitabilityService {
    private final SupplyLineService supplyLineService;
    private final ProductService productService;
    private final ResponseBuilder responseBuilder;

    public ResponseEntity<MultipleEntityResponse> calculateProfitability(FinancialRequest request) {
        List<ProfitabilityResponse> list = productService.findAll().stream()
                .map(product -> this.calculateProfitability(product, request))
                .toList();
        return responseBuilder.buildMultipleEntityResponse(list);
    }

    public ResponseEntity<SingleEntityResponse> calculateProfitability(long productId, FinancialRequest request) {
        Product product = productService.findById(productId);
        return responseBuilder.buildSingleEntityResponse(calculateProfitability(product, request));
    }

    private ProfitabilityResponse calculateProfitability(Product product, FinancialRequest request) {
        double averageSaleCost = getAverageSaleCost(product.getSalesHistory(), request);
        double averageSupplyCost = composeIngredientsAverageCost(product.getTechProcess().getIngredients(), request);
        return ProfitabilityResponseBuilder.withProduct(product)
                .withPurchaseCost(averageSupplyCost)
                .withSaleCost(averageSaleCost)
                .createResponse();
    }

    private double composeIngredientsAverageCost(Map<Material, Integer> ingredients, FinancialRequest request) {

        return ingredients.entrySet().stream()
                .map(entry -> {
                    List<SupplyLine> supplyLineList = supplyLineService.findAllByMaterial(entry.getKey());
                    return getAverageSupplyCost(supplyLineList, entry.getValue(), request);
                })
                .reduce(0.0, Double::sum);
    }

    private double getAverageSupplyCost(List<SupplyLine> supplyLineList, int quantity, FinancialRequest request) {
        double totalPurchaseCost = supplyLineList.stream()
                .filter(PredicateUtils.forSupplyLine().predicate(request))
                .mapToDouble(SupplyLine::getPurchaseCost)
                .reduce(0.0, Double::sum);
        return (totalPurchaseCost / supplyLineList.size()) * quantity;
    }
    
    private double getAverageSaleCost(List<OrderLine> orderLineList, FinancialRequest request) {
        double totalSaleCost = orderLineList.stream()
                .filter(PredicateUtils.forOrderLine().predicate(request))
                .mapToDouble(OrderLine::getSaleCost)
                .reduce(0.0, Double::sum);
        return totalSaleCost / orderLineList.size();
    }

}
