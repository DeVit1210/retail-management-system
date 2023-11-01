package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.mapper.supply.SupplyLineMapper;
import by.bsuir.retail.repository.supply.SupplyLineRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.request.supply.SupplyLineAddingRequest;
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
public class SupplyLineService {
    private final SpecificationService specificationService;
    private final SupplyLineRepository supplyLineRepository;
    private final ResponseBuilder responseBuilder;
    private final SupplyLineMapper mapper;
    public SupplyLine findById(long supplyLineId) {
        return supplyLineRepository.findById(supplyLineId)
                .orElseThrow(() -> new WrongRetailEntityIdException(SupplyLine.class, supplyLineId));
    }
    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<SupplyLine> supplyLineList = supplyLineRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toSupplyLineDtoList(supplyLineList));
    }

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<SupplyLine> specificationChain =
                specificationService.createSpecificationChain(request, SupplyLine.class);
        List<SupplyLine> supplyLineList = supplyLineRepository.findAll(specificationChain);
        return responseBuilder.buildMultipleEntityResponse(mapper.toSupplyLineDtoList(supplyLineList));
    }

    public void createSupplyLines(SupplyAddingRequest request) {
        List<SupplyLineAddingRequest> supplyLineRequestList = mapper.toSupplyLineRequestList(request);
        List<SupplyLine> supplyLineList = mapper.toSupplyLineList(supplyLineRequestList);
        supplyLineRepository.saveAll(supplyLineList);
    }

    public ResponseEntity<SingleEntityResponse> getById(long supplyLineId) {
        return responseBuilder.buildSingleEntityResponse(findById(supplyLineId));
    }
}
