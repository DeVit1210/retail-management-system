package by.bsuir.retail.mapper.products;

import by.bsuir.retail.dto.products.TechProcessDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.request.products.TechProcessAddingRequest;
import by.bsuir.retail.service.products.MaterialService;
import by.bsuir.retail.service.products.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TechProcessMapper {
    @Autowired
    protected ProductService productService;
    @Autowired
    protected MaterialService materialService;
    @Mapping(target = "processId", source = "techProcess.id")
    @Mapping(target = "createdProductName", source = "techProcess.createdProduct.name")
    @Mapping(target = "createdProductWeight", source = "techProcess.createdProduct.weight")
    @Mapping(target = "ingredientNameAndQuantity", expression = "java(mapIngredients(techProcess))")
    public abstract TechProcessDto toTechProcessDto(TechProcess techProcess);

    @Mapping(target = "createdProduct", expression = "java(productService.findById(request.getProductId()))")
    @Mapping(target = "ingredients", expression = "java(mapIngredients(request))")
    public abstract TechProcess toTechProcess(TechProcessAddingRequest request);

    public abstract List<TechProcessDto> toTechProcessDtoList(List<TechProcess> techProcessList);

    protected Map<String, Integer> mapIngredients(TechProcess techProcess) {
        return techProcess.getIngredients().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
    }

    protected Map<Material, Integer> mapIngredients(TechProcessAddingRequest request) {
        return request.getProcessComposition().entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> materialService.findById(entry.getKey()), Map.Entry::getValue));
    }
}
