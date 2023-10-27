package by.bsuir.retail.mapper.supply;

import by.bsuir.retail.dto.supply.SupplyDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supply;
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

@Mapper(componentModel = "spring")
public abstract class SupplyMapper {
    @Autowired
    protected CoffeeShopService coffeeShopService;
    @Autowired
    protected SupplierService supplierService;
    @Autowired
    protected MaterialService materialService;
    @Mapping(target = "coffeeShopId", source = "supply.coffeeShop.id")
    @Mapping(target = "coffeeShopName", source = "supply.coffeeShop.name")
    @Mapping(target = "supplierName", source = "supply.supplier.companyName")
    @Mapping(target = "supplyComposition", expression = "java(mapComposition(supply))")
    public abstract SupplyDto toSupplyDto(Supply supply);

    @Mapping(target = "coffeeShop", expression = "java(coffeeShopService.findById(request.getCoffeeShopId()))")
    @Mapping(target = "supplier", expression = "java(supplierService.findById(request.getSupplierId()))")
    @Mapping(target = "composition", expression = "java(mapComposition(request))")
    public abstract Supply toSupply(SupplyAddingRequest request);

    public abstract List<SupplyDto> toSupplyDtoList(List<Supply> supplyList);

    protected Map<String, Integer> mapComposition(Supply supply) {
        return supply.getComposition().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
    }

    protected List<Material> mapMaterialIdList(List<Long> materialIdList) {
        return materialIdList.stream()
                .map(materialId -> materialService.findById(materialId))
                .toList();
    }

    protected Map<Material, Integer> mapComposition(SupplyAddingRequest request) {
        List<Material> materialList = mapMaterialIdList(request.getMaterialIdList());
        List<Integer> materialQuantityList = request.getMaterialQuantityList();
        if(materialList.size() != materialQuantityList.size()) {
            throw new IllegalArgumentException("list sizes must be equal!");
        }
        Map<Material, Integer> result = new HashMap<>();
        IntStream.range(0, materialList.size()).forEach(value -> {
            result.put(materialList.get(value), materialQuantityList.get(value));
        });
        return result;
    }
}
