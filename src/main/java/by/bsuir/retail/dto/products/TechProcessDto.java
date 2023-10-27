package by.bsuir.retail.dto.products;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class TechProcessDto implements RetailManagementEntity {
    private long processId;
    private String createdProductName;
    private String createdProductWeight;
    private Map<String, Integer> ingredientNameAndQuantity;
}
