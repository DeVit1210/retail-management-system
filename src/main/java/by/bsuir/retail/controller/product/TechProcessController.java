package by.bsuir.retail.controller.product;

import by.bsuir.retail.request.products.TechProcessAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.products.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/techProcess")
public class TechProcessController {
    private final KitchenService kitchenService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllTechProcess() {
        return kitchenService.findAll();
    }
    @GetMapping("/{techProcessId}")
    public ResponseEntity<SingleEntityResponse> getTechProcess(@PathVariable long techProcessId) {
        return kitchenService.getById(techProcessId);
    }
    @PostMapping
    public ResponseEntity<SingleEntityResponse> addTechProcess(@RequestBody TechProcessAddingRequest request) {
        return kitchenService.addTechProcess(request);
    }
}