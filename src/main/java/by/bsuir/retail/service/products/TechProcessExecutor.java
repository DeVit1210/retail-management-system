package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.TechProcess;

import java.util.Map;

public class TechProcessExecutor {
    public static void execute(Map<Material, Integer> coffeeShopWarehouse, TechProcess techProcess, int times) {
        Map<Material, Integer> ingredients = techProcess.getIngredients();
        ingredients.forEach((material, quantityForOneProduct) -> {
            Integer totalMaterialQuantity = quantityForOneProduct * times;
            Integer currentMaterialLeftover = coffeeShopWarehouse.get(material);
            coffeeShopWarehouse.replace(material, currentMaterialLeftover - totalMaterialQuantity);
        });
    }

}
