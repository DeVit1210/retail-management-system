package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.sales.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    Payment findByOrder(Order order);
}
