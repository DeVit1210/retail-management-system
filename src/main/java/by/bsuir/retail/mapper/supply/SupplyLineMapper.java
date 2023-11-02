package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplyLineDto;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.request.supply.SupplyLineAddingRequest;
import by.bsuir.retail.service.products.MaterialService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class SupplyLineMapper {
    @Autowired
    protected MaterialService materialService;
    @Mapping(target = "materialName", source = "material.name")
    public abstract SupplyLineDto toSupplyLineDto(SupplyLine supplyLine);
    @Mapping(target = "material", expression = "java(materialService.findById(request.getMaterialId()))")
    @Mapping(target = "purchasedAt",  expression = "java(java.time.LocalDateTime.now())")
    public abstract SupplyLine toSupplyLine(SupplyLineAddingRequest request);
    public abstract List<SupplyLineDto> toSupplyLineDtoList(List<SupplyLine> supplyLineList);
    public List<SupplyLine> toSupplyLineList(SupplyAddingRequest request) {
        List<Long> materialIdList = request.getMaterialIdList();
        List<Integer> materialQuantityList = request.getMaterialQuantityList();
        List<Double> materialCostList = request.getMaterialCostList();

        return IntStream.range(0, materialIdList.size())
                .mapToObj(value -> SupplyLineAddingRequest.builder()
                        .materialId(materialIdList.get(value))
                        .purchaseCost(materialCostList.get(value))
                        .quantity(materialQuantityList.get(value))
                        .build()
                )
                .map(this::toSupplyLine)
                .toList();
    }
}
