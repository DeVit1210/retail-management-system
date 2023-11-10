package by.bsuir.retail.repository.supply;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.SupplyLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SupplyLineRepository extends JpaRepository<SupplyLine, Long>, JpaSpecificationExecutor<SupplyLine> {
    List<SupplyLine> findAllByMaterial(Material material);
}
