package by.bsuir.retail.service;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.mapper.CoffeeShopMapper;
import by.bsuir.retail.repository.CoffeeShopRepository;
import by.bsuir.retail.request.CoffeeShopAddingRequest;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoffeeShopService {
    private final SpecificationService specificationService;
    private final CoffeeShopRepository coffeeShopRepository;
    private final CoffeeShopMapper mapper;
    private final ResponseBuilder responseBuilder;
    public CoffeeShop findById(long coffeeShopId) {
        return coffeeShopRepository.findById(coffeeShopId)
                .orElseThrow(() -> new IllegalArgumentException("coffee shop is not found with id: " + coffeeShopId));
    }

    public ResponseEntity<MultipleEntityResponse> findAll() {
        List<CoffeeShop> coffeeShopList = coffeeShopRepository.findAll();
        return responseBuilder.buildMultipleEntityResponse(mapper.toCoffeeShopDtoList(coffeeShopList));
    }

    public ResponseEntity<MultipleEntityResponse> findAll(SearchQueryRequest request) {
        Specification<CoffeeShop> specificationChain
                = specificationService.createSpecificationChain(request, CoffeeShop.class);
        List<CoffeeShop> coffeeShopList = coffeeShopRepository.findAll(specificationChain);
        return responseBuilder.buildMultipleEntityResponse(mapper.toCoffeeShopDtoList(coffeeShopList));
    }

    public ResponseEntity<SingleEntityResponse> addCoffeeShop(CoffeeShopAddingRequest request) {
        CoffeeShop coffeeShop = mapper.toCoffeeShop(request);
        CoffeeShop savedCoffeeShop = coffeeShopRepository.save(coffeeShop);
        return responseBuilder.buildSingleEntityResponse(mapper.toCoffeeShopDto(savedCoffeeShop));
    }

    public CoffeeShop findCoffeeShopByOrder(Order order) {
        return order.getCashier().getCoffeeShop();
    }

    public void updateCoffeeShop(CoffeeShop coffeeShop) {
        coffeeShopRepository.save(coffeeShop);
    }

    public Map<Material, Integer> getWarehouse(long coffeeShopId) {
        CoffeeShop coffeeShop = findById(coffeeShopId);
        return coffeeShop.getWarehouse();
    }
}
