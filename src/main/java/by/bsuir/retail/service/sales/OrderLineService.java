package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.OrderLine;
import by.bsuir.retail.repository.sales.OrderLineRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    public OrderLine findById(long orderLineId) {
        return orderLineRepository.findById(orderLineId)
                .orElseThrow(() -> new WrongRetailEntityIdException(OrderLine.class, orderLineId));
    }
}
