package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.context.annotation.PropertySource;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class CashierTestBuilder implements TestBuilder<Cashier> {
    private String username = "username";
    private String password = "password";
    private boolean enabled = false;
    private String fullName = "fullName";
    private CoffeeShop coffeeShop = CoffeeShopTestBuilder.builder().build();
    @Override
    public Cashier build() {
        return Cashier.builder()
                .login(username)
                .password(password)
                .enabled(enabled)
                .fullName(fullName)
                .coffeeShop(coffeeShop)
                .build();
    }
}
