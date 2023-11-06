package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.mapper.supply.SupplyMapper;
import by.bsuir.retail.repository.supply.SupplyRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
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
public class SupplyService {
    private final SpecificationService specificationService;
    private final SupplyLineService supplyLineService;
    private final SupplyRepository supplyRepository;
    private final ResponseBuilder responseBuilder;
    private final SupplyMapper mapper;
    public Supply findById(long supplyId) {
        return supplyRepository.findById(supplyId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Supply.class, supplyId));
    }

    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }

    public List<Supply> findAll(SearchQueryRequest request) {
        Specification<Supply> specificationChain = specificationService.createSpecificationChain(request, Supply.class);
        return supplyRepository.findAll(specificationChain);
    }

    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toSupplyDtoList(findAll()));
    }

    public ResponseEntity<MultipleEntityResponse> getAll(SearchQueryRequest request) {
        return responseBuilder.buildMultipleEntityResponse(mapper.toSupplyDtoList(findAll(request)));
    }

    public ResponseEntity<SingleEntityResponse> addSupply(SupplyAddingRequest request) {
        Supply supply = mapper.toSupply(request);
        supply.setTotalCost(calculateTotalSupplyCost(supply));
        Supply savedSupply = supplyRepository.save(supply);
        supplyLineService.saveSupplyLines(savedSupply);
        return responseBuilder.buildSingleEntityResponse(mapper.toSupplyDto(savedSupply));
    }

    public ResponseEntity<SingleEntityResponse> getById(long supplyId) {
        return responseBuilder.buildSingleEntityResponse(findById(supplyId));
    }

    private double calculateTotalSupplyCost(Supply supply) {
        return supply.getComposition().stream()
                .mapToDouble(supplyLine -> supplyLine.getPurchaseCost() * supplyLine.getQuantity())
                .reduce(0.0, Double::sum);
    }

}
