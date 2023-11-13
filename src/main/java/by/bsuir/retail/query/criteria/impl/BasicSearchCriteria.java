package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;


public class BasicSearchCriteria extends SearchCriteria<Object> {
    public BasicSearchCriteria(String fieldName, Operator operator, Object value) {
        super(fieldName, operator, value);
    }
}
