package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.mapper.supply.SupplierMapper;
import by.bsuir.retail.repository.supply.SupplierRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.supply.SupplierAddingRequest;
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
public class SupplierService {
    private final SpecificationService specificationService;
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

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<Supplier> specificationChain =
                specificationService.createSpecificationChain(request, Supplier.class);
        List<Supplier> supplierList = supplierRepository.findAll(specificationChain);
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

    public ResponseEntity<SingleEntityResponse> editSupplier(long supplierId, SupplierAddingRequest request) {
        Supplier supplier = mapper.toSupplier(request);
        supplier.setId(supplierId);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return responseBuilder.buildSingleEntityResponse(mapper.toSupplierDto(savedSupplier));
    }
}
