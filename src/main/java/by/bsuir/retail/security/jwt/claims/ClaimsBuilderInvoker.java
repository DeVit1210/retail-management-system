package by.bsuir.retail.security.jwt.claims;

import by.bsuir.retail.entity.users.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimsBuilderInvoker {
    private final BeanFactory beanFactory;
    public ClaimsBuilder newClaimsBuilder(GrantedAuthority grantedAuthority) {
        return switch (Role.fromAuthority(grantedAuthority)) {
            case CASHIER -> beanFactory.getBean(CashierClaimsBuilder.class);
            case COFFEE_SHOP_MANAGER -> beanFactory.getBean(CoffeeShopManagerClaimsBuilder.class);
            case NETWORK_MANAGER -> beanFactory.getBean(NetworkManagerClaimsBuilder.class);
        };
    }


}
