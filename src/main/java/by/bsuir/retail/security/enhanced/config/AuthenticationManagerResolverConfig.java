package by.bsuir.retail.security.enhanced.config;

import by.bsuir.retail.security.enhanced.manager.CashierAuthenticationManager;
import by.bsuir.retail.security.enhanced.manager.CoffeeShopManagerAuthenticationManager;
import by.bsuir.retail.security.enhanced.manager.NetworkManagerAuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManagerResolver;

import java.util.function.BiPredicate;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerResolverConfig {
    private final CashierAuthenticationManager cashierAuthenticationManager;
    private final NetworkManagerAuthenticationManager networkManagerAuthenticationManager;
    private final CoffeeShopManagerAuthenticationManager coffeeShopManagerAuthenticationManager;
    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver() {
        return httpServletRequest -> {
            BiPredicate<HttpServletRequest, String> pathMatcher = ((request, s) -> request.getPathInfo().startsWith(s));
            if(pathMatcher.test(httpServletRequest, "/cashier")) {
                return cashierAuthenticationManager;
            } else if (pathMatcher.test(httpServletRequest, "/manager/network")) {
                return networkManagerAuthenticationManager;
            } else return coffeeShopManagerAuthenticationManager;
        };
    }

}
