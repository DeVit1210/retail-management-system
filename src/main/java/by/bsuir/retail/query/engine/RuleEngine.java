package by.bsuir.retail.query.engine;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.criteria.SearchCriteria;
import by.bsuir.retail.query.criteria.SearchCriteriaFactory;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleEngine {
    private SearchCriteriaFactory searchCriteriaFactory;
    public Rule process(SearchCriteriaDto dto) {
        SearchCriteria searchCriteria = searchCriteriaFactory.create(dto);
        return buildRuleList(searchCriteria).stream()
                .filter(Rule::evaluate)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("search criteria is not matching any rule!"));
    }

    private List<Rule> buildRuleList(SearchCriteria criteria) {
        return List.of(
                new EqualsOperatorRule(criteria),
                new NotEqualsOperatorRule(criteria),
                new LowerOperatorRule(criteria),
                new LowerEqualsOperatorRule(criteria),
                new GreaterOperatorRule(criteria),
                new GreaterEqualsOperatorRule(criteria),
                new TrueOperatorRule(criteria),
                new FalseOperatorRule(criteria)
        );
    }
}


