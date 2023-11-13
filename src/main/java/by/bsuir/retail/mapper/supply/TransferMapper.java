package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.TransferDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Transfer;
import by.bsuir.retail.request.supply.TransferAddingRequest;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.products.MaterialService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransferMapper {
    @Autowired
    protected CoffeeShopService coffeeShopService;
    @Autowired
    protected MaterialService materialService;

    @Mapping(target = "toCoffeeShop", expression = "java(coffeeShopService.findById(request.getToCoffeeShopId()))")
    @Mapping(target = "fromCoffeeShop", expression = "java(coffeeShopService.findById(request.getFromCoffeeShopId()))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "material", expression = "java(materialService.findById(request.getMaterialId()))")
    public abstract Transfer toTransfer(TransferAddingRequest request);

    @Mapping(target = "toCoffeeShopName", source = "transfer.toCoffeeShop.name")
    @Mapping(target = "fromCoffeeShopName", source = "transfer.fromCoffeeShop.name")
    @Mapping(target = "materialName", source = "transfer.material.name")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd hh:mm")
    public abstract TransferDto toTransferDto(Transfer transfer);

    public abstract List<TransferDto> toTransferDtoList(List<Transfer> transferList);
}
