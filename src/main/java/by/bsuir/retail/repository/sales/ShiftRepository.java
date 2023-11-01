package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.Shift;
import by.bsuir.retail.entity.users.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByCashier(Cashier cashier);
    Optional<Shift> findByCashierAndActiveIsTrue(Cashier cashier);
    List<Shift> findByActiveIsTrue();
}
