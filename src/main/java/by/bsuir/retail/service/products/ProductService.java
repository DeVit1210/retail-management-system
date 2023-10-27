package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.repository.products.ProductRepository;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Product findById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Product.class, productId));
    }

    public double calculateProductCost(long productId, int discountPercent) {
        final double defaultCost = findById(productId).getSaleCost();
        final double discountAmount = (defaultCost / 100) * discountPercent;
        return defaultCost - discountAmount;
    }
}
