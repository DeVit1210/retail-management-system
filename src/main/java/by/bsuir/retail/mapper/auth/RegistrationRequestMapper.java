package by.bsuir.retail.mapper.auth;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.request.auth.RegistrationRequest;
import by.bsuir.retail.service.CoffeeShopService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class RegistrationRequestMapper {
    @Autowired
    protected CoffeeShopService coffeeShopService;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Mapping(target = "login", source = "request.username")
    @Mapping(target = "coffeeShop", expression = "java(coffeeShopService.findById(request.getCoffeeShopId()))")
    @Mapping(target = "enabled", expression = "java(false)")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    public abstract Cashier mapToCashier(RegistrationRequest request);

    @Mapping(target = "login", source = "request.username")
    @Mapping(target = "managedCoffeeShop", expression = "java(coffeeShopService.findById(request.getCoffeeShopId()))")
    @Mapping(target = "enabled", expression = "java(false)")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    public abstract CoffeeShopManager mapToCoffeeShopManager(RegistrationRequest request);
}
