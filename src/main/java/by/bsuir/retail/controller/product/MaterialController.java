package by.bsuir.retail.controller.product;

import by.bsuir.retail.request.products.MaterialAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.products.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/material")
public class MaterialController {
    private final MaterialService materialService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllMaterials() {
        return materialService.findAll();
    }
    @GetMapping("/{materialId}")
    public ResponseEntity<SingleEntityResponse> getMaterial(@PathVariable long materialId) {
        return materialService.getById(materialId);
    }
    @PostMapping
    public ResponseEntity<SingleEntityResponse> addMaterial(@RequestBody MaterialAddingRequest request) {
        return materialService.addMaterial(request);
    }
}
