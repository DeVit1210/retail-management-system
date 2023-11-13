package by.bsuir.retail.query.engine;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class RuleEngine {
    protected List<Rule> ruleList;
    protected SearchCriteriaMapper mapper;
    public abstract void init(SearchCriteriaDto criteriaDto);
    public Rule process() {
        return ruleList.stream()
                .filter(Rule::evaluate)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("search criteria is not matching any rule!"));
    }
}
