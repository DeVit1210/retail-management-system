package by.bsuir.retail.response.entity;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SingleEntityResponse {
    private RetailManagementEntity response;
}
