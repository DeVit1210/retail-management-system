package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;

public class IntegerSearchCriteria extends SearchCriteria<Integer> {
    public IntegerSearchCriteria(String fieldName, Operator operator, Integer value) {
        super(fieldName, operator, value);
    }
}
