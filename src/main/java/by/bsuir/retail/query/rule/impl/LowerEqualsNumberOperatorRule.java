package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LowerEqualsNumberOperatorRule extends Rule {
    @Override
    public boolean evaluate() {
        return criteria.getOperator().equals(Operator.LOWER_OR_EQUALS) && criteria.getValue() instanceof Number;
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.le(root.get(criteria.getFieldName()), (Number) criteria.getValue());
    }
}
