package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.HashMap;
import java.util.Map;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class TechProcessTestBuilder implements TestBuilder<TechProcess> {
    private Product product = ProductTestBuilder.builder().build();
    private Map<Material, Integer> ingredients = new HashMap<>();
    @Override
    public TechProcess build() {
        return TechProcess.builder()
                .createdProduct(product)
                .ingredients(ingredients)
                .build();
    }
}
