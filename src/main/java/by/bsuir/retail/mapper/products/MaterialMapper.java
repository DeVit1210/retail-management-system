package by.bsuir.retail.mapper.products;

import by.bsuir.retail.dto.products.MaterialDto;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.request.products.MaterialAddingRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    MaterialDto toMaterialDto(Material material);
    Material toMaterial(MaterialAddingRequest request);
}
