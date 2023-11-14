package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.supply.Transfer;
import by.bsuir.retail.mapper.supply.TransferMapper;
import by.bsuir.retail.repository.supply.TransferRepository;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.request.supply.TransferAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.CoffeeShopService;
import by.bsuir.retail.service.WarehouseService;
import by.bsuir.retail.service.query.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final SpecificationService specificationService;
    private final TransferRepository transferRepository;
    private final WarehouseService warehouseService;
    private final CoffeeShopService coffeeShopService;
    private final ResponseBuilder responseBuilder;
    private final TransferMapper mapper;

    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    public List<Transfer> findAll(SearchQueryRequest request) {
        Specification<Transfer> specification = specificationService.createSpecificationChain(request, Transfer.class);
        return transferRepository.findAll();
    }

    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toTransferDtoList(findAll()));
    }

    public ResponseEntity<MultipleEntityResponse> getAll(SearchQueryRequest request) {
        return responseBuilder.buildMultipleEntityResponse(mapper.toTransferDtoList(findAll(request)));
    }

    public ResponseEntity<SingleEntityResponse> createTransfer(TransferAddingRequest request) {
        Transfer transfer = mapper.toTransfer(request);
        warehouseService.updateWarehouse(transfer);
        Transfer savedTransfer = transferRepository.save(transfer);
        return responseBuilder.buildSingleEntityResponse(mapper.toTransferDto(savedTransfer));
    }

    public ResponseEntity<MultipleEntityResponse> getAllBetween(long fromId, long toId) {
        CoffeeShop from = coffeeShopService.findById(fromId);
        CoffeeShop to = coffeeShopService.findById(toId);
        List<Transfer> transferList = transferRepository.findAllBetweenCoffeeShops(from, to);
        return responseBuilder.buildMultipleEntityResponse(mapper.toTransferDtoList(transferList));
    }

    public ResponseEntity<MultipleEntityResponse> getAllFrom(long fromCoffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(fromCoffeeShopId);
        List<Transfer> transferList = transferRepository.findAllByFromCoffeeShop(coffeeShop);
        return responseBuilder.buildMultipleEntityResponse(mapper.toTransferDtoList(transferList));
    }

    public ResponseEntity<MultipleEntityResponse> getAllTo(long toCoffeeShopId) {
        CoffeeShop coffeeShop = coffeeShopService.findById(toCoffeeShopId);
        List<Transfer> transferList = transferRepository.findAllByToCoffeeShop(coffeeShop);
        return responseBuilder.buildMultipleEntityResponse(mapper.toTransferDtoList(transferList));
    }
}
