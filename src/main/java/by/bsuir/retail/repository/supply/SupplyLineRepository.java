package by.bsuir.retail.repository.supply;

import by.bsuir.retail.entity.supply.SupplyLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyLineRepository extends JpaRepository<SupplyLine, Long> {
}
