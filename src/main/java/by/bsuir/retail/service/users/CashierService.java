package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.Role;
import by.bsuir.retail.mapper.user.CashierMapper;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.exception.UserNotFoundException;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.utils.ThrowableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CashierService {
    private final CashierRepository cashierRepository;
    private final CashierMapper mapper;
    private final ResponseBuilder responseBuilder;
    private final CoffeeShopService coffeeShopService;
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

    public ResponseEntity<MultipleEntityResponse> findAllByCoffeeShop(long coffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(coffeeShopId);
        List<Cashier> cashierList = cashierRepository.findAllByCoffeeShopAndEnabled(coffeeShop, true);
        return responseBuilder.buildMultipleEntityResponse(mapper.toCashierDtoList(cashierList));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Cashier> cashierList = cashierRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toCashierDtoList(cashierList));
    }

    public Cashier getAuthenticatedCashier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ThrowableUtils.prepareTest(authentication, Objects::nonNull).orElseThrow(UserNotFoundException.class);
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return findByUsername(username);
    }
}
