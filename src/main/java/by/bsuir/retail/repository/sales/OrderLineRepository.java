package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
