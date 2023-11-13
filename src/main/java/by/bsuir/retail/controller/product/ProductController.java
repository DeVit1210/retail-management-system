package by.bsuir.retail.controller.product;

import by.bsuir.retail.request.products.ProductAddingRequest;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{productId}")
    public ResponseEntity<SingleEntityResponse> getProduct(@PathVariable long productId) {
        return productService.getById(productId);
    }
    @GetMapping
    public ResponseEntity<MultipleEntityResponse> getAllProducts() {
        return productService.getAll();
    }
    @PostMapping
    public ResponseEntity<SingleEntityResponse> addProduct(@RequestBody ProductAddingRequest request) {
        return productService.addProduct(request);
    }
}
