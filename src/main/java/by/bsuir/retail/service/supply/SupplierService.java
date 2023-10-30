package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.mapper.supply.SupplierMapper;
import by.bsuir.retail.repository.supply.SupplierRepository;
import by.bsuir.retail.request.supply.SupplierAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ResponseBuilder responseBuilder;
    private final SupplierMapper mapper;
    public Supplier findById(long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Supplier.class, supplierId));
    }
    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Supplier> supplierList = supplierRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toSupplierDtoList(supplierList));
    }

    public ResponseEntity<SingleEntityResponse> addSupplier(SupplierAddingRequest request) {
        Supplier supplier = mapper.toSupplier(request);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return responseBuilder.buildSingleEntityResponse(mapper.toSupplierDto(savedSupplier));
    }

    public ResponseEntity<SingleEntityResponse> getById(long supplierId) {
        return responseBuilder.buildSingleEntityResponse(findById(supplierId));
    }
}
