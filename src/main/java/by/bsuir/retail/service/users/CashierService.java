package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.request.sales.OrderAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.UserNotFoundException;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.sales.OrderService;
import by.bsuir.retail.service.sales.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CashierService {
    private final CashierRepository cashierRepository;
    public Cashier findByUsername(String username) {
        return cashierRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(Role.CASHIER));
    }
    public Cashier findById(long cashierId) {
        return cashierRepository.findById(cashierId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Cashier.class, cashierId));
    }
    public Cashier saveCashier(Cashier cashier) {
        return cashierRepository.save(cashier);
    }
    public void deleteCashier(long id) {
        cashierRepository.deleteById(id);
    }

}
