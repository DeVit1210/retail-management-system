package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.ShiftMapper;
import by.bsuir.retail.repository.sales.ShiftRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.users.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final ResponseBuilder responseBuilder;
    private final OrderService orderService;
    private final CashierService cashierService;
    private final ShiftMapper mapper;
    public Shift findById(long shiftId) {
        return shiftRepository.findById(shiftId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Shift.class, shiftId));
    }

    public List<Shift> findByCashier(Cashier cashier) {
        return shiftRepository.findByCashier(cashier);
    }

    public Shift findActiveByCashier(Cashier cashier) {
        return shiftRepository.findByCashierAndActiveIsTrue(cashier)
                .orElseThrow(() -> new IllegalStateException("cashier is not working now!"));
    }

    public ResponseEntity<MultipleEntityResponse> getCurrentShiftHistory(long cashierId) {
        Cashier cashier = cashierService.findById(cashierId);
        Shift activeShift = findActiveByCashier(cashier);
        return orderService.getShiftOrderList(activeShift);
    }

    public ResponseEntity<MultipleEntityResponse> getShiftHistory(long cashierId) {
        Cashier cashier = cashierService.findById(cashierId);
        List<Shift> shiftHistory = findByCashier(cashier);
        List<Double> shiftTotalIncomeList = shiftHistory.stream().map(orderService::getShiftTotalIncome).toList();
        return responseBuilder.buildMultipleEntityResponse(mapper.toShiftDtoList(shiftHistory, shiftTotalIncomeList));
    }

    // TODO: add CreatedResponse (with id and ResponseStatus.CREATED(201))
    public void openShift(long cashierId) {
        Cashier cashier = cashierService.findById(cashierId);
        Shift shift = Shift.builder()
                .startTime(LocalDateTime.now())
                .cashier(cashier)
                .active(true)
                .build();
        shiftRepository.save(shift);
    }

    public ResponseEntity<SingleEntityResponse> closeShift(long cashierId) {
        Cashier cashier = cashierService.findById(cashierId);
        Shift finishedShift = findActiveByCashier(cashier).toBuilder()
                .endTime(LocalDateTime.now())
                .active(false)
                .build();
        double shiftTotalIncome = orderService.getShiftTotalIncome(finishedShift);
        return responseBuilder.buildSingleEntityResponse(mapper.toShiftDto(finishedShift, shiftTotalIncome));
    }
}
