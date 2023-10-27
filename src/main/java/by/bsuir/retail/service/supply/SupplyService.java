package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.repository.supply.SupplyRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyRepository supplyRepository;
    public Supply findById(long supplyId) {
        return supplyRepository.findById(supplyId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Supply.class, supplyId));
    }
}
