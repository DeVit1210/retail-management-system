package by.bsuir.retail.service.sales;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.mapper.sales.ShiftMapper;
import by.bsuir.retail.repository.sales.ShiftRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.exception.ShiftAlreadyOpenedException;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.utils.ThrowableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final ResponseBuilder responseBuilder;
    private final OrderService orderService;
    private final CoffeeShopService coffeeShopService;
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

    public ResponseEntity<MultipleEntityResponse> getCurrentShiftHistory(Cashier cashier) {
        Shift activeShift = findActiveByCashier(cashier);
        return orderService.getShiftOrderList(activeShift);
    }

    public ResponseEntity<MultipleEntityResponse> getCoffeeShopActiveShifts(long coffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(coffeeShopId);
        List<Cashier> cashierList = coffeeShop.getCashierList().stream().filter(UserDetails::isEnabled).toList();
        List<Shift> shiftList = shiftRepository.findByCashierInAndActiveIsTrue(cashierList);
        List<Double> totalIncomeList = mapToTotalShiftIncomeList(shiftList);
        return responseBuilder.buildMultipleEntityResponse(mapper.toShiftDtoList(shiftList, totalIncomeList));
    }

    public ResponseEntity<MultipleEntityResponse> getShiftHistory(Cashier cashier) {
        List<Shift> shiftHistory = findByCashier(cashier);
        List<Double> shiftTotalIncomeList = mapToTotalShiftIncomeList(shiftHistory);
        return responseBuilder.buildMultipleEntityResponse(mapper.toShiftDtoList(shiftHistory, shiftTotalIncomeList));
    }

    // TODO: add CreatedResponse (with id and ResponseStatus.CREATED(201))
    public void openShift(Cashier cashier) {
        ThrowableUtils.prepareTest(cashier, c -> !shiftRepository.existsByCashierAndActiveIsTrue(c))
                .orElseThrow(new ShiftAlreadyOpenedException(cashier.getId()));
        Shift shift = Shift.builder().startTime(LocalDateTime.now()).cashier(cashier).active(true).build();
        shiftRepository.save(shift);
    }

    public ResponseEntity<SingleEntityResponse> closeShift(Cashier cashier) {
        Shift finishedShift = findActiveByCashier(cashier).toBuilder()
                .endTime(LocalDateTime.now()).active(false)
                .build();
        shiftRepository.save(finishedShift);
        double shiftTotalIncome = orderService.getShiftTotalIncome(finishedShift);
        return responseBuilder.buildSingleEntityResponse(mapper.toShiftDto(finishedShift, shiftTotalIncome));
    }

    public ResponseEntity<MultipleEntityResponse> getAllActiveShiftsStatistics() {
        List<Shift> activeShiftList = shiftRepository.findByActiveIsTrue();
        List<Double> totalIncomeList = mapToTotalShiftIncomeList(activeShiftList);
        return responseBuilder.buildMultipleEntityResponse(mapper.toShiftDtoList(activeShiftList, totalIncomeList));
    }

    public ResponseEntity<MultipleEntityResponse> getCoffeeShopShiftHistory(long coffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(coffeeShopId);
        List<Cashier> cashierList = coffeeShop.getCashierList().stream().filter(UserDetails::isEnabled).toList();
        List<Shift> shiftList = shiftRepository.findByCashierIn(cashierList);
        List<Double> totalIncomeList = mapToTotalShiftIncomeList(shiftList);
        return responseBuilder.buildMultipleEntityResponse(mapper.toShiftDtoList(shiftList, totalIncomeList));
    }

    private List<Double> mapToTotalShiftIncomeList(List<Shift> shiftList) {
        return shiftList.stream().map(orderService::getShiftTotalIncome).toList();
    }

    private List<Shift> getCoffeeShopActiveShifts(CoffeeShop coffeeShop) {
        return coffeeShop.getCashierList().stream()
                .map(shiftRepository::findByCashierAndActiveIsTrue)
                .filter(Optional::isPresent).map(Optional::get)
                .toList();
    }
}
