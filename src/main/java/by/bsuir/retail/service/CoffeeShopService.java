package by.bsuir.retail.service;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.sales.Order;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.supply.SupplyLine;
import by.bsuir.retail.mapper.CoffeeShopMapper;
import by.bsuir.retail.repository.CoffeeShopRepository;
import by.bsuir.retail.request.CoffeeShopAddingRequest;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
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
    public CoffeeShop save(CoffeeShop coffeeShop) {
        return coffeeShopRepository.save(coffeeShop);
    }
    public CoffeeShop findById(long coffeeShopId) {
        return coffeeShopRepository.findById(coffeeShopId)
                .orElseThrow(() -> new IllegalArgumentException("coffee shop is not found with id: " + coffeeShopId));
    }

    public List<CoffeeShop> findAll() {
        return coffeeShopRepository.findAll();
    }

    public List<CoffeeShop> findAll(SearchQueryRequest request) {
        return specificationService.executeQuery(request, coffeeShopRepository::findAll, CoffeeShop.class);
    }


    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toCoffeeShopDtoList(findAll()));
    }

    public ResponseEntity<MultipleEntityResponse> getAll(SearchQueryRequest request) {
        return responseBuilder.buildMultipleEntityResponse(mapper.toCoffeeShopDtoList(findAll(request)));
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

    public void updateWarehouse(Supply supply) {
        CoffeeShop coffeeShop = supply.getCoffeeShop();
        Map<Material, Integer> warehouse = coffeeShop.getWarehouse();
        List<SupplyLine> supplyComposition = supply.getComposition();
        supplyComposition.forEach(supplyLine -> {
            Material material = supplyLine.getMaterial();
            Integer currentQuantity = warehouse.getOrDefault(material, 0);
            if(currentQuantity == 0) {
                warehouse.put(material, supplyLine.getQuantity());
            } else warehouse.replace(material, supplyLine.getQuantity() + currentQuantity);
        });
    }
}
