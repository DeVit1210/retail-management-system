package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.mapper.products.TechProcessMapper;
import by.bsuir.retail.repository.products.TechProcessRepository;
import by.bsuir.retail.request.products.TechProcessAddingRequest;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.WarehouseService;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService {
    private final SpecificationService specificationService;
    private final WarehouseService warehouseService;
    private final ResponseBuilder responseBuilder;
    private final TechProcessRepository techProcessRepository;
    private final TechProcessMapper mapper;
    public TechProcess findById(long techProcessId) {
        return techProcessRepository.findById(techProcessId)
                .orElseThrow(() -> new WrongRetailEntityIdException(TechProcess.class, techProcessId));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<TechProcess> techProcessList = techProcessRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toTechProcessDtoList(techProcessList));
    }

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        List<TechProcess> techProcessList =
                specificationService.executeQuery(request, techProcessRepository::findAll, TechProcess.class);
        return responseBuilder.buildMultipleEntityResponse(mapper.toTechProcessDtoList(techProcessList));
    }

    public ResponseEntity<SingleEntityResponse> addTechProcess(TechProcessAddingRequest request) {
        TechProcess techProcess = mapper.toTechProcess(request);
        TechProcess savedTechProcess = techProcessRepository.save(techProcess);
        return responseBuilder.buildSingleEntityResponse(mapper.toTechProcessDto(savedTechProcess));
    }

    public ResponseEntity<SingleEntityResponse> getById(long techProcessId) {
        return responseBuilder.buildSingleEntityResponse(findById(techProcessId));
    }

    public TechProcess findByProduct(Product product) {
        return techProcessRepository.findByCreatedProduct(product)
                .orElseThrow(() -> new IllegalArgumentException("tech process for this product is not found!"));
    }

    public void prepareOrder(Order order) {
        warehouseService.updateWarehouse(order);
    }
}
