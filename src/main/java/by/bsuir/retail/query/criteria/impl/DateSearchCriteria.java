package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;

import java.time.LocalDate;
public class DateSearchCriteria extends SearchCriteria<LocalDate> {
    public DateSearchCriteria(String fieldName, Operator operator, LocalDate value) {
        super(fieldName, operator, value);
    }
}
