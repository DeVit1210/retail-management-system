package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.repository.products.MaterialRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    public Material findById(long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Material.class, materialId));
    }
}
