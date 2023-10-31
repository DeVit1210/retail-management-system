package by.bsuir.retail.service.query;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.engine.RuleEngineBuilder;
import by.bsuir.retail.query.rule.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SpecificationBuilderService {
    private final RuleEngineBuilder ruleEngineBuilder;
    public <T> Specification<T> buildSpecification(SearchCriteriaDto dto, Class<T> aClass) {
        RuleEngine engine = ruleEngineBuilder.buildRuleEngine(dto);
        Rule process = engine.process();
        return process::getResult;
    }

    public <T> Specification<T> createSpecificationChain(List<SearchCriteriaDto> dtoList, Class<T> aClass) {
        List<Specification<T>> specificationList = dtoList.stream()
                .map(dto -> this.buildSpecification(dto, aClass))
                .toList();
        Specification<T> result = specificationList.get(0);
        for(int i = 1; i<specificationList.size(); i++) {
            result = Specification.where(result).and(specificationList.get(i));
        }
        return result;
    }
}
