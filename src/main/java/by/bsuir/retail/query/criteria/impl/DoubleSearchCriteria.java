package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DoubleSearchCriteria extends SearchCriteria {
    private double value;
    public DoubleSearchCriteria(String fieldName, Operator operator, double value) {
        super(fieldName, operator);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public <T> Predicate handleGreater(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.gt(root.get(fieldName), value);
    }

    @Override
    public <T> Predicate handleGreaterEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.ge(root.get(fieldName), value);
    }

    @Override
    public <T> Predicate handleLower(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.lt(root.get(fieldName), value);
    }

    @Override
    public <T> Predicate handleLowerEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.le(root.get(fieldName), value);
    }
}
