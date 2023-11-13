package by.bsuir.retail.dto.supply;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplierDto implements RetailManagementEntity {
    private long id;
    private String companyName;
    private String phoneNumber;
    private String address;
    private String email;
    private int supplyQuantity;
}
