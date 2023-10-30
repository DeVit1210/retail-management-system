package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GreaterNumberOperatorRule extends Rule {
    @Override
    public boolean evaluate() {
        return criteria.getOperator().equals(Operator.GREATER) && criteria.getValue() instanceof Number;
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.gt(root.get(criteria.getFieldName()), (Number) criteria.getValue());
    }
}
