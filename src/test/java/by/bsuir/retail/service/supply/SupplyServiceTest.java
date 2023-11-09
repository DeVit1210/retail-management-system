package by.bsuir.retail.service.supply;

import by.bsuir.retail.dto.supply.SupplyDto;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.mapper.supply.SupplyMapper;
import by.bsuir.retail.repository.supply.SupplyRepository;
import by.bsuir.retail.request.supply.SupplyAddingRequest;
import by.bsuir.retail.response.buidler.ResponseBuilder;
import by.bsuir.retail.response.entity.SingleEntityResponse;
import by.bsuir.retail.service.WarehouseService;
import by.bsuir.retail.testbuilder.impl.SupplyTestBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class SupplyServiceTest {
    @InjectMocks
    private SupplyService supplyService;
    @Mock
    private SupplyLineService supplyLineService;
    @Mock
    private SupplyRepository supplyRepository;
    @Mock
    private WarehouseService warehouseService;
    @Mock
    private SupplyMapper supplyMapper;
    @Mock
    private ResponseBuilder responseBuilder;

    @Test
    void testAddSupplySuccess() {
        Supply supply = SupplyTestBuilder.builder().build();
        SupplyDto supplyDto = mock(SupplyDto.class);
        configureMocksForAddSupplyTest(supply, supplyDto);
        SupplyAddingRequest request = mock(SupplyAddingRequest.class);
        ResponseEntity<SingleEntityResponse> response = supplyService.addSupply(request);
        assertEquals(Objects.requireNonNull(response.getBody()).getResponse(), supplyDto);
        verifyAddSupplyTest(supply, request);
    }

    private void configureMocksForAddSupplyTest(Supply supply, SupplyDto supplyDto) {
        when(supplyMapper.toSupply(any(SupplyAddingRequest.class))).thenReturn(supply);
        doNothing().when(warehouseService).updateWarehouse(any(Supply.class));
        when(supplyRepository.save(any(Supply.class))).thenReturn(supply);
        doNothing().when(supplyLineService).saveSupplyLines(any(Supply.class));
        ResponseEntity<SingleEntityResponse> response = ResponseEntity.ok(SingleEntityResponse.builder().response(supplyDto).build());
        when(responseBuilder.buildSingleEntityResponse(any(RetailManagementEntity.class))).thenReturn(response);
        when(supplyMapper.toSupplyDto(any(Supply.class))).thenReturn(supplyDto);
    }

    private void verifyAddSupplyTest(Supply supply, SupplyAddingRequest request) {
        verify(supplyMapper, times(1)).toSupply(request);
        verify(supplyLineService, times(1)).saveSupplyLines(supply);
        verify(supplyRepository, times(1)).save(supply);
        verify(warehouseService, times(1)).updateWarehouse(supply);
        verify(supplyMapper, times(1)).toSupplyDto(supply);
    }

}