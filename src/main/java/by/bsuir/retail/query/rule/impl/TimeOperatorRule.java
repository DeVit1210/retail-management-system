package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.impl.TimeSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class TimeOperatorRule implements Rule {
    protected TimeSearchCriteria criteria;
}
