package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.users.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByCashierAndCreatedAtBetween(Cashier cashier, LocalDateTime start, LocalDateTime end);
    List<Order> findAllByCashierIn(List<Cashier> cashierList);
}
