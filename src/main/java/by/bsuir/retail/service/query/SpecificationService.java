package by.bsuir.retail.service.query;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.request.query.SearchQueryRequest;
import by.bsuir.retail.utils.SpecificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class SpecificationService {
    private final RuleEngine engine;
    private <T> Specification<T> buildSpecification(SearchCriteriaDto dto, Class<T> clazz) {
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

    public <T> Sort sortedBy(SearchQueryRequest request, Class<T> clazz) {
        return Sort.by(
                request.isAscendingSort() ? Sort.Direction.ASC : Sort.Direction.DESC,
                request.getSortFieldName()
        );
    }


    public <T> List<T> executeQuery(SearchQueryRequest request,
                                    BiFunction<Specification<T>, Sort, List<T>> repositoryCallFunction,
                                    Class<T> tClass) {
        Specification<T> specification = createSpecificationChain(request, tClass);
        Sort sort = sortedBy(request, tClass);
        return repositoryCallFunction.apply(specification, sort);
    }
}
