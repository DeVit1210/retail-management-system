package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashierService {
    private final CashierRepository cashierRepository;
    public Cashier findByUsername(String username) {
        return cashierRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(Role.CASHIER));
    }
    public Cashier saveCashier(Cashier cashier) {
        return cashierRepository.save(cashier);
    }
    public void deleteCashier(long id) {
        cashierRepository.deleteById(id);
    }
}
