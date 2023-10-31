package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.EqualsOperatorRule;
import by.bsuir.retail.query.rule.impl.FalseOperatorRule;
import by.bsuir.retail.query.rule.impl.NotEqualsOperatorRule;
import by.bsuir.retail.query.rule.impl.TrueOperatorRule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasicRuleEngine extends RuleEngine {
    public BasicRuleEngine(List<Rule> ruleList, SearchCriteriaMapper mapper) {
        super(ruleList, mapper);
    }
    @Override
    public void init(SearchCriteriaDto dto) {
        BasicSearchCriteria basicSearchCriteria = mapper.toBasicSearchCriteria();
        this.ruleList = List.of(
                new NotEqualsOperatorRule(basicSearchCriteria),
                new EqualsOperatorRule(basicSearchCriteria),
                new TrueOperatorRule(basicSearchCriteria),
                new FalseOperatorRule(basicSearchCriteria)
        );
    }
}
