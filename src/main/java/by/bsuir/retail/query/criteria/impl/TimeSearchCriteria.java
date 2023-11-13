package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;

import java.time.LocalDateTime;

public class TimeSearchCriteria extends SearchCriteria<LocalDateTime> {
    public TimeSearchCriteria(String fieldName, Operator operator, LocalDateTime value) {
        super(fieldName, operator, value);
    }
}
