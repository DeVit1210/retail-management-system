package by.bsuir.retail.repository.products;

import by.bsuir.retail.entity.products.TechProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechProcessRepository extends JpaRepository<TechProcess, Long> {
}
