package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplyDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.products.MaterialService;
import by.bsuir.retail.service.supply.SupplierService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public abstract class SupplyMapper {
    @Autowired
    protected CoffeeShopService coffeeShopService;
    @Autowired
    protected SupplierService supplierService;
    @Autowired
    protected SupplyLineMapper supplyLineMapper;
    @Mapping(target = "coffeeShopId", source = "supply.coffeeShop.id")
    @Mapping(target = "coffeeShopName", source = "supply.coffeeShop.name")
    @Mapping(target = "supplierName", source = "supply.supplier.companyName")
    @Mapping(target = "supplyComposition", expression = "java(mapComposition(supply))")
    public abstract SupplyDto toSupplyDto(Supply supply);

    @Mapping(target = "coffeeShop", expression = "java(coffeeShopService.findById(request.getCoffeeShopId()))")
    @Mapping(target = "supplier", expression = "java(supplierService.findById(request.getSupplierId()))")
    @Mapping(target = "composition", expression = "java(supplyLineMapper.toSupplyLineList(request))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    public abstract Supply toSupply(SupplyAddingRequest request);

    public abstract List<SupplyDto> toSupplyDtoList(List<Supply> supplyList);

    protected Map<String, Integer> mapComposition(Supply supply) {
        return supply.getComposition().stream()
                .collect(Collectors.toMap(supplyLine -> supplyLine.getMaterial().getName(), SupplyLine::getQuantity));
    }

}
