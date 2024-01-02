package by.bsuir.retail.controller.supply;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.supply.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supply")
public class SupplyController {
    private final SupplyService supplyService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllSupply() {
        return supplyService.getAll();
    }
    @GetMapping("/{supplyId}")
    public ResponseEntity<SingleEntityResponse> getSupply(@PathVariable long supplyId) {
        return supplyService.getById(supplyId);
    }
    @PostMapping
    public ResponseEntity<SingleEntityResponse> addSupply(@RequestBody SupplyAddingRequest request) {
        return supplyService.addSupply(request);
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<MultipleEntityResponse> getAllBySupplier(@PathVariable long supplierId) {
        return supplyService.findBySupplier(supplierId);
    }
}