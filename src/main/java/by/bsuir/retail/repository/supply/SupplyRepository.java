package by.bsuir.retail.repository.supply;

import by.bsuir.retail.entity.supply.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long>, JpaSpecificationExecutor<Supply> {
    List<Supply> findAllBySupplier_Id(long supplierId);
}
