package by.bsuir.retail.service.users;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.repository.users.CashierRepository;
import by.bsuir.retail.service.exception.UserNotFoundException;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.testbuilder.impl.CashierTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class CashierServiceTest {
    @Mock
    private CashierRepository cashierRepository;
    @InjectMocks
    private CashierService cashierService;
    @Value("${test.cashier.username}")
    private String cashierUsername;
    @Test
    void testFindByUsernameSuccess() {
        Cashier cashier = CashierTestBuilder.builder().build();
        when(cashierRepository.findByUsername(anyString())).thenReturn(Optional.of(cashier));
        assertDoesNotThrow(() -> cashierService.findByUsername(cashierUsername));
        verify(cashierRepository, times(1)).findByUsername(cashierUsername);
    }
    @Test
    void testFindByUsernameNotFound() {
        when(cashierRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cashierService.findByUsername(cashierUsername))
                .isInstanceOf(UserNotFoundException.class);
        verify(cashierRepository, times(1)).findByUsername(cashierUsername);
    }
    @Test
    void testFindByIdSuccess() {
        Cashier cashier = CashierTestBuilder.builder().build();
        when(cashierRepository.findById(anyLong())).thenReturn(Optional.of(cashier));
        assertDoesNotThrow(() -> cashierService.findById(1L));
        verify(cashierRepository, times(1)).findById(1L);
    }
    @Test
    void testFindByIdNotFound() {
        when(cashierRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cashierService.findById(1L)).isInstanceOf(WrongRetailEntityIdException.class);
        verify(cashierRepository, times(1)).findById(1L);
    }
}