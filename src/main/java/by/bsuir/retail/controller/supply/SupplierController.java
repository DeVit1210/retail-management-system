package by.bsuir.retail.controller.supply;

import by.bsuir.retail.request.supply.SupplierAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.supply.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllSupplier() {
        return supplierService.findAll();
    }
    @GetMapping("/{supplierId}")
    public ResponseEntity<SingleEntityResponse> getSupplier(@PathVariable long supplierId) {
        return supplierService.getById(supplierId);
    }
    @PostMapping
    public ResponseEntity<SingleEntityResponse> addSupplier(@RequestBody SupplierAddingRequest request) {
        return supplierService.addSupplier(request);
    }
}
