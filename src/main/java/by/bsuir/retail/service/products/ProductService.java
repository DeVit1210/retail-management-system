package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.repository.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product with such id was not found: " + productId));
    }
}
