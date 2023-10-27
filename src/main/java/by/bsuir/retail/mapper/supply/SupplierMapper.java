package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplierDto;
import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.request.supply.SupplierAddingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    @Mapping(target = "supplyQuantity", expression = "java(supplier.getSupplyList().size())")
    SupplierDto toSupplierDto(Supplier supplier);
    Supplier toSupplier(SupplierAddingRequest request);
}
