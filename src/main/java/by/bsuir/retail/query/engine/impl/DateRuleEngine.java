package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.criteria.impl.DateSearchCriteria;
import by.bsuir.retail.query.engine.RuleEngine;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateRuleEngine extends RuleEngine {
    @Autowired
    public DateRuleEngine(SearchCriteriaMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(SearchCriteriaDto dto) {
        DateSearchCriteria dateSearchCriteria = mapper.toDateSearchCriteria(dto);
        BasicSearchCriteria basicSearchCriteria = mapper.toBasicSearchCriteria(dto);
        this.ruleList = List.of(
                new LowerDateOperatorRule(dateSearchCriteria),
                new GreaterDateOperatorRule(dateSearchCriteria),
                new LowerEqualsDateOperatorRule(dateSearchCriteria),
                new GreaterDateOperatorRule(dateSearchCriteria),
                new EqualsOperatorRule(basicSearchCriteria),
                new NotEqualsOperatorRule(basicSearchCriteria)
        );
    }
}
