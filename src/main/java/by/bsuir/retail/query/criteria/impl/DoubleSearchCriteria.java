package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;

public class DoubleSearchCriteria extends SearchCriteria<Number> {
    public DoubleSearchCriteria(String fieldName, Operator operator, Number value) {
        super(fieldName, operator, value);
    }
}
