package by.bsuir.retail.response.entity;

import by.bsuir.retail.entity.RetailManagementEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MultipleEntityResponse {
    private List<? extends RetailManagementEntity> response;
}
