package by.bsuir.retail.controller.supply;

import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.supply.SupplyLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplyLine")
public class SupplyLineController {
    private final SupplyLineService supplyLineService;
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllSupplyLine() {
        return supplyLineService.findAll();
    }
    @GetMapping("/{supplyLineId}")
    public ResponseEntity<SingleEntityResponse> getSupplyLine(@PathVariable long supplyLineId) {
        return supplyLineService.getById(supplyLineId);
    }
}
