package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.mapper.products.TechProcessMapper;
import by.bsuir.retail.repository.products.TechProcessRepository;
import by.bsuir.retail.request.products.TechProcessAddingRequest;
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
public class TechProcessService {
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

    public ResponseEntity<SingleEntityResponse> addTechProcess(TechProcessAddingRequest request) {
        TechProcess techProcess = mapper.toTechProcess(request);
        TechProcess savedTechProcess = techProcessRepository.save(techProcess);
        return responseBuilder.buildSingleEntityResponse(mapper.toTechProcessDto(savedTechProcess));
    }
}
