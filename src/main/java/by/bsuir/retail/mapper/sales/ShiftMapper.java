package by.bsuir.retail.mapper.sales;

import by.bsuir.retail.dto.sales.ShiftDto;
import by.bsuir.retail.entity.sales.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    @Mapping(target = "cashierName", source = "shift.cashier.fullName")
    @Mapping(target = "startTime", source = "shift.startTime", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "endTime", source = "shift.endTime", dateFormat = "yyyy-MM-dd HH:mm")
    ShiftDto toShiftDto(Shift shift, Double totalIncome);

    default List<ShiftDto> toShiftDtoList(List<Shift> shiftList, List<Double> totalIncomeList) {
        return IntStream.range(0, shiftList.size())
                .mapToObj(value -> toShiftDto(shiftList.get(value), totalIncomeList.get(value)))
                .toList();
    }
}
