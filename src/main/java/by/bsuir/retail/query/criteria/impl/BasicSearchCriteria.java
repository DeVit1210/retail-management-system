package by.bsuir.retail.query.criteria.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class BasicSearchCriteria extends SearchCriteria {
    private final Object value;
    public BasicSearchCriteria(String fieldName, Operator operator, Object value) {
        super(fieldName, operator);
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public <T> Predicate handleGreater(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Predicate handleGreaterEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Predicate handleLower(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Predicate handleLowerEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        throw new UnsupportedOperationException();
    }
}
