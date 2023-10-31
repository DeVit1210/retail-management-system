package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BasicOperatorRule implements Rule {
    protected BasicSearchCriteria criteria;
}
