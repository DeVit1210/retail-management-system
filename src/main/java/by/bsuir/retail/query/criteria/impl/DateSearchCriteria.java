package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
public class DateSearchCriteria extends SearchCriteria {
    private final LocalDate value;
    public DateSearchCriteria(String fieldName, Operator operator, LocalDate value) {
        super(fieldName, operator);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public <T> Predicate handleGreater(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.greaterThan(root.get(fieldName), value);
    }

    @Override
    public <T> Predicate handleGreaterEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.greaterThanOrEqualTo(root.get(fieldName), value);
    }

    @Override
    public <T> Predicate handleLower(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.lessThan(root.get(fieldName), value);
    }

    @Override
    public <T> Predicate handleLowerEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.lessThanOrEqualTo(root.get(fieldName), value);
    }
}
