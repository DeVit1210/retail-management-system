package by.bsuir.retail.request.query;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.criteria.SearchCriteria;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchQueryRequest {
    private List<SearchCriteriaDto> searchCriteriaList;
}
