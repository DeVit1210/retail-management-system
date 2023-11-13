package by.bsuir.retail.testbuilder.impl;

import by.bsuir.retail.entity.supply.Supplier;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.testbuilder.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@With
@NoArgsConstructor(staticName = "builder")
@AllArgsConstructor
public class SupplierTestBuilder implements TestBuilder<Supplier> {
    private String companyName = "supplier company name";
    private String phoneNumber = "supplier phone number";
    private String address = "supplier address";
    private String email = "supplier email";
    private List<Supply> supplyList = new ArrayList<>();
    @Override
    public Supplier build() {
        return Supplier.builder()
                .companyName(companyName)
                .phoneNumber(phoneNumber)
                .address(address)
                .email(email)
                .supplyList(supplyList)
                .build();
    }
}
