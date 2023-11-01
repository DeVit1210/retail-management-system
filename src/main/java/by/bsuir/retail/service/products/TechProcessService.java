package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.mapper.products.TechProcessMapper;
import by.bsuir.retail.repository.products.TechProcessRepository;
import by.bsuir.retail.request.products.TechProcessAddingRequest;
import by.bsuir.retail.request.query.SearchQueryRequest;
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
public class TechProcessService {
    private final SpecificationService specificationService;
    private final TechProcessRepository techProcessRepository;
    private final ResponseBuilder responseBuilder;
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
        Specification<TechProcess> specificationChain =
                specificationService.createSpecificationChain(request, TechProcess.class);
        List<TechProcess> techProcessList = techProcessRepository.findAll(specificationChain);
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
}
