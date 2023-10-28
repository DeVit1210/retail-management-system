package by.bsuir.retail.mapper;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.request.auth.RegistrationRequest;
import by.bsuir.retail.service.CoffeeShopService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RegistrationRequestMapper {
    @Autowired
    protected CoffeeShopService coffeeShopService;
    @Mapping(target = "login", source = "request.username")
    @Mapping(target = "coffeeShop", expression = "java(coffeeShopService.findById(request.getCoffeeShopId()))")
    @Mapping(target = "enabled", expression = "java(false)")
    public abstract Cashier mapToCashier(RegistrationRequest request);

    @Mapping(target = "login", source = "request.username")
    @Mapping(target = "managedCoffeeShop", expression = "java(coffeeShopService.findById(request.getCoffeeShopId()))")
    @Mapping(target = "enabled", expression = "java(false)")
    public abstract CoffeeShopManager mapToCoffeeShopManager(RegistrationRequest request);
}
