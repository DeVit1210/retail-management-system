package by.bsuir.retail.dto.supply;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplierDto {
    private long id;
    private String companyName;
    private String phoneNumber;
    private String address;
    private String email;
    private int supplyQuantity;
}
