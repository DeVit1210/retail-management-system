package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.impl.DateSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class DateOperatorRule implements Rule {
    protected DateSearchCriteria criteria;
}
