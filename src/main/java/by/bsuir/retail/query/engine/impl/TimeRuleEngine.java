package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.impl.TimeSearchCriteria;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.GreaterEqualsTimeOperatorRule;
import by.bsuir.retail.query.rule.impl.GreaterTimeOperatorRule;
import by.bsuir.retail.query.rule.impl.LowerEqualsTimeOperatorRule;
import by.bsuir.retail.query.rule.impl.LowerTimeOperatorRule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeRuleEngine extends RuleEngine {
    public TimeRuleEngine(SearchCriteriaMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(SearchCriteriaDto dto) {
        TimeSearchCriteria timeSearchCriteria = mapper.toTimeSearchCriteria(dto);
        this.ruleList = List.of(
                new LowerTimeOperatorRule(timeSearchCriteria),
                new GreaterTimeOperatorRule(timeSearchCriteria),
                new LowerEqualsTimeOperatorRule(timeSearchCriteria),
                new GreaterEqualsTimeOperatorRule(timeSearchCriteria)
        );
    }
}
