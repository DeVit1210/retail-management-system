package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.impl.DoubleSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class NumberOperatorRule implements Rule {
    protected DoubleSearchCriteria criteria;
}