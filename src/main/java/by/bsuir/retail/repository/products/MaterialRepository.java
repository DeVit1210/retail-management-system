package by.bsuir.retail.repository.products;

import by.bsuir.retail.entity.products.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {
    List<Material> findAllByNameContainingIgnoreCase(String input);
}
