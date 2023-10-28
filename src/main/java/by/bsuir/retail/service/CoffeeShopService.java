package by.bsuir.retail.service;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.mapper.CoffeeShopMapper;
import by.bsuir.retail.repository.CoffeeShopRepository;
import by.bsuir.retail.request.CoffeeShopAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeShopService {
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
    public ResponseEntity<SingleEntityResponse> addCoffeeShop(CoffeeShopAddingRequest request) {
        CoffeeShop coffeeShop = mapper.toCoffeeShop(request);
        CoffeeShop savedCoffeeShop = coffeeShopRepository.save(coffeeShop);
        return responseBuilder.buildSingleEntityResponse(mapper.toCoffeeShopDto(savedCoffeeShop));
    }
}
