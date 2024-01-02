package by.bsuir.retail.mapper.user;

import by.bsuir.retail.dto.user.CashierDto;
import by.bsuir.retail.entity.users.Cashier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CashierMapper {
    @Mapping(target = "coffeeShopId", source = "cashier.coffeeShop.id")
    @Mapping(target = "coffeeShopName", source = "cashier.coffeeShop.name")
    @Mapping(target = "shiftQuantity", expression = "java(cashier.getShiftList().size())")
    CashierDto toCashierDto(Cashier cashier);
    List<CashierDto> toCashierDtoList(List<Cashier> cashierList);
}
