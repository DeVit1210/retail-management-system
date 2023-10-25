package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
