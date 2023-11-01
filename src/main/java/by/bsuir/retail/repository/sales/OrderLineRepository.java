package by.bsuir.retail.repository.sales;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.sales.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long>, JpaSpecificationExecutor<OrderLine> {
    List<OrderLine> findAllByProduct(Product product);
}
