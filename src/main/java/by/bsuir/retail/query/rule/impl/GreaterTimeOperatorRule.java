package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GreaterTimeOperatorRule extends TimeOperatorRule {
    @Override
    public boolean evaluate() {
        return criteria.getOperator().equals(Operator.GREATER);
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.greaterThan(root.get(criteria.getFieldName()), criteria.getValue());
    }
}
