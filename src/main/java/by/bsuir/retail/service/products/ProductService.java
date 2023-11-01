package by.bsuir.retail.service.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.mapper.products.ProductMapper;
import by.bsuir.retail.repository.products.ProductRepository;
import by.bsuir.retail.request.products.ProductAddingRequest;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.exception.WrongRetailEntityIdException;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final SpecificationService specificationService;
    private final ProductRepository productRepository;
    private final ResponseBuilder responseBuilder;
    private final ProductMapper mapper;

    public Product findById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new WrongRetailEntityIdException(Product.class, productId));
    }

    public ResponseEntity<SingleEntityResponse> getById(long productId) {
        return responseBuilder.buildSingleEntityResponse(findById(productId));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<Product> productList = productRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(productList));
    }

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<Product> specificationChain = specificationService.createSpecificationChain(request, Product.class);
        List<Product> productList = productRepository.findAll(specificationChain);
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(productList));
    }

    public double calculateProductCost(long productId, int discountPercent) {
        final double defaultCost = findById(productId).getSaleCost();
        final double discountAmount = (defaultCost / 100) * discountPercent;
        return defaultCost - discountAmount;
    }

    public ResponseEntity<SingleEntityResponse> addProduct(ProductAddingRequest request) {
        Product product = mapper.toProduct(request);
        Product savedProduct = productRepository.save(product);
        return responseBuilder.buildSingleEntityResponse(mapper.toProductDto(savedProduct));
    }

}
