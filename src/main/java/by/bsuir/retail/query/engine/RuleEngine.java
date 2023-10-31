package by.bsuir.retail.query.engine;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.SearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public abstract class RuleEngine {
    protected List<Rule> ruleList;
    protected SearchCriteriaMapper mapper;
    public abstract void init(SearchCriteriaDto criteriaDto);
}
