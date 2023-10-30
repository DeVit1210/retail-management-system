package by.bsuir.retail.dto.query;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchCriteriaDto {
    private String fieldName;
    private String operatorSignature;
    private String valueType;
    private String value;
}
