package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.criteria.impl.DoubleSearchCriteria;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.rule.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NumberRuleEngine extends RuleEngine {
    @Autowired
    public NumberRuleEngine(SearchCriteriaMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(SearchCriteriaDto dto) {
        DoubleSearchCriteria numberSearchCriteria = mapper.toNumberSearchCriteria(dto);
        BasicSearchCriteria basicSearchCriteria = mapper.toBasicSearchCriteria(dto);
        this.ruleList = List.of(
                new LowerNumberOperatorRule(numberSearchCriteria),
                new GreaterNumberOperatorRule(numberSearchCriteria),
                new LowerEqualsNumberOperatorRule(numberSearchCriteria),
                new GreaterEqualsNumberOperatorRule(numberSearchCriteria),
                new EqualsOperatorRule(basicSearchCriteria),
                new NotEqualsOperatorRule(basicSearchCriteria)
        );
    }
}
