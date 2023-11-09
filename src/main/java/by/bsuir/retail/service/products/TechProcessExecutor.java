package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.service.exception.NotSufficientMaterialAmountException;
import by.bsuir.retail.utils.ThrowableUtils;

import java.util.Map;

public class TechProcessExecutor {
    public static void execute(Map<Material, Integer> coffeeShopWarehouse, TechProcess techProcess, int times) {
        Map<Material, Integer> ingredients = techProcess.getIngredients();
        ingredients.forEach((material, quantityForOneProduct) -> {
            Integer totalQuantity = quantityForOneProduct * times;
            Integer currentLeftover = coffeeShopWarehouse.getOrDefault(material, 0);
            ThrowableUtils.prepareTest(currentLeftover, leftover -> leftover >= totalQuantity)
                            .orElseThrow(new NotSufficientMaterialAmountException(currentLeftover, totalQuantity, material.getName()));
            coffeeShopWarehouse.replace(material, currentLeftover - totalQuantity);
        });
    }
}
