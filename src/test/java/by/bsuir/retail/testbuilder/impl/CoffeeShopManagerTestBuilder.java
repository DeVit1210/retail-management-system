package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CoffeeShopManagerTestBuilder implements TestBuilder<CoffeeShopManager> {
    private String username = "username";
    private String password = "password";
    private boolean enabled = false;
    private String fullName = "fullName";
    private CoffeeShop managedCoffeeShop = new CoffeeShop();

    @Override
    public CoffeeShopManager build() {
        return CoffeeShopManager.builder()
                .login(username)
                .password(password)
                .managedCoffeeShop(managedCoffeeShop)
                .enabled(enabled)
                .fullName(fullName)
                .build();
    }
}
