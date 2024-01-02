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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAll(SearchQueryRequest request) {
        Specification<Product> specificationChain = specificationService.createSpecificationChain(request, Product.class);
        return productRepository.findAll(specificationChain);
    }

    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(findAll()));
    }


    public ResponseEntity<MultipleEntityResponse> getAll(SearchQueryRequest request) {
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(findAll(request)));
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

    public ResponseEntity<SingleEntityResponse> editProduct(long productId, ProductAddingRequest request) {
        Product product = mapper.toProduct(request);
        product.setId(productId);
        Product savedProduct = productRepository.save(product);
        return responseBuilder.buildSingleEntityResponse(mapper.toProductDto(savedProduct));
    }

    public ResponseEntity<MultipleEntityResponse> findAllWithoutTechProcess() {
        List<Product> withoutTechProcess = productRepository.findAll().stream()
                .filter(product -> Objects.isNull(product.getTechProcess()))
                .toList();
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(withoutTechProcess));
    }

    public List<Product> findAllWithTechProcess() {
        return productRepository.findAll().stream()
                .filter(product -> Objects.nonNull(product.getTechProcess()))
                .toList();
    }

    public ResponseEntity<MultipleEntityResponse> findAllContaining(String productName) {
        List<Product> productList = productRepository.findAllByNameContainingIgnoreCase(productName);
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(productList));
    }

    public ResponseEntity<MultipleEntityResponse> findAllWithTechProcessAndContaining(String productName) {
        List<Product> productList = productRepository.findAllByNameContainingIgnoreCase(productName)
                .stream()
                .filter(product -> Objects.nonNull(product.getTechProcess()))
                .toList();
        return responseBuilder.buildMultipleEntityResponse(mapper.toProductDtoList(productList));
    }
}
