package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.mapper.supply.SupplyMapper;
import by.bsuir.retail.repository.supply.SupplyRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyRepository supplyRepository;
    private final ResponseBuilder responseBuilder;
    private final SupplyMapper mapper;
    public Supply findById(long supplyId) {
        return supplyRepository.findById(supplyId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Supply.class, supplyId));
    }
    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Supply> supplyList = supplyRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toSupplyDtoList(supplyList));
    }
}
