package by.bsuir.retail.security.enhanced.config;

import by.bsuir.retail.security.enhanced.manager.CashierAuthenticationManager;
import by.bsuir.retail.security.enhanced.manager.CoffeeShopManagerAuthenticationManager;
import by.bsuir.retail.security.enhanced.manager.NetworkManagerAuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.function.BiPredicate;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerResolverConfig {
    private CashierAuthenticationManager cashierAuthenticationManager;
    private NetworkManagerAuthenticationManager networkManagerAuthenticationManager;
    private CoffeeShopManagerAuthenticationManager coffeeShopManagerAuthenticationManager;
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
