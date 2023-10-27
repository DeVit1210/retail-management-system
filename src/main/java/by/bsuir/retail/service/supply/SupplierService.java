package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.repository.supply.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    public Supplier findById(long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("supplier with such id was not found: " + supplierId));
    }
}
