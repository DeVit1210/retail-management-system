package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.ShiftDto;
import by.bsuir.retail.entity.sales.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    @Mapping(target = "cashierName", source = "shift.cashier.fullName")
    @Mapping(target = "startTime", source = "shift.startTime", dateFormat = "HH:mm dd-MM-yyyy")
    @Mapping(target = "endTime", source = "shift.endTime", dateFormat = "HH:mm dd-MM-yyyy")
    @Mapping(target = "coffeeShopName", source = "shift.cashier.coffeeShop.name")
    ShiftDto toShiftDto(Shift shift, Double totalIncome);

    default List<ShiftDto> toShiftDtoList(List<Shift> shiftList, List<Double> totalIncomeList) {
        return IntStream.range(0, shiftList.size())
                .mapToObj(value -> toShiftDto(shiftList.get(value), totalIncomeList.get(value)))
                .toList();
    }
}
