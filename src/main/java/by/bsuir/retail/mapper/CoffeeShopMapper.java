package by.bsuir.retail.mapper;

import by.bsuir.retail.dto.CoffeeShopDto;
import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.request.CoffeeShopAddingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CoffeeShopMapper {
    @Mapping(target = "warehouse", expression = "java(mapWarehouse(coffeeShop))")
    CoffeeShopDto toCoffeeShopDto(CoffeeShop coffeeShop);
    CoffeeShop toCoffeeShop(CoffeeShopAddingRequest request);
    List<CoffeeShopDto> toCoffeeShopDtoList(List<CoffeeShop> coffeeShopList);

    default Map<String, Integer> mapWarehouse(CoffeeShop coffeeShop) {
        return coffeeShop.getWarehouse().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
    }
}
