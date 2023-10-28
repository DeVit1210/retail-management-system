package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.mapper.supply.SupplierMapper;
import by.bsuir.retail.repository.supply.SupplierRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.Query;
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
}
