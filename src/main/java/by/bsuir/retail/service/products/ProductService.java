package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.mapper.products.ProductMapper;
import by.bsuir.retail.repository.products.ProductRepository;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ResponseBuilder responseBuilder;
    private final ProductMapper mapper;
    public Product findById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Product.class, productId));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Product> productList = productRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(productList));
    }

    public double calculateProductCost(long productId, int discountPercent) {
        final double defaultCost = findById(productId).getSaleCost();
        final double discountAmount = (defaultCost / 100) * discountPercent;
        return defaultCost - discountAmount;
    }

}
