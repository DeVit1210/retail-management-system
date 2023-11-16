package by.bsuir.retail.service.query;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.utils.SpecificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecificationService {
    private final RuleEngine engine;
    public <T> Specification<T> buildSpecification(SearchCriteriaDto dto, Class<T> clazz) {
        Rule process = engine.process(dto);
        return process::getResult;
    }

    public <T> Specification<T> createSpecificationChain(SearchQueryRequest request, Class<T> clazz) {
        if(request == null) return SpecificationUtils.empty();
        List<Specification<T>> specificationList = request.getSearchCriteriaList().stream()
                .map(dto -> this.buildSpecification(dto, clazz))
                .toList();
        Specification<T> result = specificationList.get(0);
        for (int i = 1; i < specificationList.size(); i++) {
            result = Specification.where(result).and(specificationList.get(i));
        }
        return result;
    }
}
