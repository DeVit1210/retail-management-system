package by.bsuir.retail.mapper;

import by.bsuir.retail.dto.CoffeeShopDto;
import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.request.CoffeeShopAddingRequest;
import by.bsuir.retail.service.CoffeeShopService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeShopMapper {
    CoffeeShopDto toCoffeeShopDto(CoffeeShop coffeeShop);
    CoffeeShop toCoffeeShop(CoffeeShopAddingRequest request);
    List<CoffeeShopDto> toCoffeeShopDtoList(List<CoffeeShop> coffeeShopList);
}
