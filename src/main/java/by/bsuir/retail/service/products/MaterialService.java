package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.mapper.products.MaterialMapper;
import by.bsuir.retail.repository.products.MaterialRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final ResponseBuilder responseBuilder;
    private final MaterialMapper mapper;
    public Material findById(long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Material.class, materialId));
    }
    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Material> materialList = materialRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toMaterialDtoList(materialList));
    }
}
