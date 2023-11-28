package by.bsuir.retail.query.criteria;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.SearchCriteria;
import by.bsuir.retail.query.engine.ValueTypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchCriteriaFactory {
    private final SearchCriteriaMapper mapper;
    public SearchCriteria create(SearchCriteriaDto dto) {
        return switch (ValueTypeResolver.resolve(dto)) {
            case "time" -> mapper.toTimeSearchCriteria(dto);
            case "date" -> mapper.toDateSearchCriteria(dto);
            case "number" -> mapper.toNumberSearchCriteria(dto);
            default -> mapper.toBasicSearchCriteria(dto);
        };
    }
}
