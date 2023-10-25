package by.bsuir.retail.repository.products;

import by.bsuir.retail.entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
