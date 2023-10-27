package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.repository.supply.SupplyLineRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplyLineService {
    private final SupplyLineRepository supplyLineRepository;

    public SupplyLine findById(long supplyLineId) {
        return supplyLineRepository.findById(supplyLineId)
                .orElseThrow(() -> new WrongRetailEntityIdException(SupplyLine.class, supplyLineId));
    }
}
