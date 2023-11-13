package by.bsuir.retail.repository.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.TechProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechProcessRepository extends JpaRepository<TechProcess, Long>, JpaSpecificationExecutor<TechProcess> {
    Optional<TechProcess> findByCreatedProduct(Product product);
}
