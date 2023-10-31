package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;

public class LowerEqualsTimeOperatorRule extends TimeOperatorRule {
    @Override
    public boolean evaluate() {
        return criteria.getOperator().equals(Operator.LOWER_OR_EQUALS);
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.lessThanOrEqualTo(root.get(criteria.getFieldName()), criteria.getValue());
    }
}
