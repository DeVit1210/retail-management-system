package by.bsuir.retail.service;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.service.products.TechProcessExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final CoffeeShopService coffeeShopService;
    public void updateWarehouse(Supply supply) {
        CoffeeShop coffeeShop = supply.getCoffeeShop();
        Map<Material, Integer> warehouse = coffeeShop.getWarehouse();
        List<SupplyLine> supplyComposition = supply.getComposition();
        supplyComposition.forEach(supplyLine -> this.updateForSupplyLine(supplyLine, warehouse));
        coffeeShopService.updateCoffeeShop(coffeeShop);
    }

    private void updateForSupplyLine(SupplyLine supplyLine, Map<Material, Integer> warehouse) {
        Material material = supplyLine.getMaterial();
        Integer currentQuantity = warehouse.getOrDefault(material, 0);
        if(currentQuantity == 0) {
            warehouse.put(material, supplyLine.getQuantity());
        } else warehouse.replace(material, supplyLine.getQuantity() + currentQuantity);
    }

    public void updateWarehouse(Order order) {
        CoffeeShop currentCoffeeShop = order.getCashier().getCoffeeShop();
        Map<Material, Integer> coffeeShopWarehouse = currentCoffeeShop.getWarehouse();
        List<OrderLine> orderComposition = order.getComposition();
        orderComposition.forEach(orderLine -> updateForOrderLine(orderLine, coffeeShopWarehouse));
        coffeeShopService.updateCoffeeShop(currentCoffeeShop);
    }

    private void updateForOrderLine(OrderLine orderLine, Map<Material, Integer> warehouse) {
        Product product = orderLine.getProduct();
        int productQuantity = orderLine.getQuantity();
        TechProcess techProcess = product.getTechProcess();
        TechProcessExecutor.execute(warehouse, techProcess, productQuantity);
    }
}
