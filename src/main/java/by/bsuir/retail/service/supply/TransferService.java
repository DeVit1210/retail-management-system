package by.bsuir.retail.service.supply;

import by.bsuir.retail.entity.supply.Transfer;
import by.bsuir.retail.mapper.supply.TransferMapper;
import by.bsuir.retail.repository.supply.TransferRepository;
import by.bsuir.retail.request.supply.TransferAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.MultipleEntityResponse;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final WarehouseService warehouseService;
    private final ResponseBuilder responseBuilder;
    private final TransferMapper mapper;

    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    public ResponseEntity<MultipleEntityResponse> getAll() {
        return responseBuilder.buildMultipleEntityResponse(mapper.toTransferDtoList(findAll()));
    }

    public ResponseEntity<SingleEntityResponse> createTransfer(TransferAddingRequest request) {
        Transfer transfer = mapper.toTransfer(request);
        warehouseService.updateWarehouse(transfer);
        Transfer savedTransfer = transferRepository.save(transfer);
        return responseBuilder.buildSingleEntityResponse(mapper.toTransferDto(savedTransfer));
    }
}
