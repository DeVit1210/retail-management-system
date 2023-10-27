package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.TechProcess;
import by.bsuir.retail.repository.products.TechProcessRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechProcessService {
    private final TechProcessRepository techProcessRepository;
    public TechProcess findById(long techProcessId) {
        return techProcessRepository.findById(techProcessId)
                .orElseThrow(() -> new WrongRetailEntityIdException(TechProcess.class, techProcessId));
    }
}
