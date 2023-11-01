package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.mapper.products.MaterialMapper;
import by.bsuir.retail.repository.products.MaterialRepository;
import by.bsuir.retail.request.products.MaterialAddingRequest;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final SpecificationService specificationService;
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

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<Material> specificationChain = specificationService.createSpecificationChain(request, Material.class);
        List<Material> materialList = materialRepository.findAll(specificationChain);
        return responseBuilder.buildMultipleEntityResponse(mapper.toMaterialDtoList(materialList));
    }

    public ResponseEntity<SingleEntityResponse> addMaterial(MaterialAddingRequest request) {
        Material material = mapper.toMaterial(request);
        Material savedMaterial = materialRepository.save(material);
        return responseBuilder.buildSingleEntityResponse(mapper.toMaterialDto(savedMaterial));
    }

    public ResponseEntity<SingleEntityResponse> getById(long materialId) {
        return responseBuilder.buildSingleEntityResponse(findById(materialId));
    }
}