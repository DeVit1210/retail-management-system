package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplyLineDto;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.service.products.MaterialService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class SupplyLineMapper {
    @Autowired
    protected MaterialService materialService;
    @Mapping(target = "materialName", source = "material.name")
    public abstract SupplyLineDto toSupplyLineDto(SupplyLine supplyLine);
    @Mapping(target = "material", expression = "java(materialService.findById(materialId))")
    @Mapping(target = "purchasedAt",  expression = "java(java.time.LocalDateTime.now())")
    public abstract SupplyLine toSupplyLine(long materialId, int quantity, double purchaseCost);
}
