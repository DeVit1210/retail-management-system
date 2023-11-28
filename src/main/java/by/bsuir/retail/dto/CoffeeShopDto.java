package by.bsuir.retail.dto;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class CoffeeShopDto implements RetailManagementEntity {
    private long id;
    private String name;
    private String address;
    private Map<String, Integer> warehouse;
}
