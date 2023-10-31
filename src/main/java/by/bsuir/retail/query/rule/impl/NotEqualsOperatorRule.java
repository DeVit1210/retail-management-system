package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.*;

public class NotEqualsOperatorRule implements Rule {
    private BasicSearchCriteria criteria;
    @Override
    public boolean evaluate() {
        return criteria.getOperator().equals(Operator.NOT_EQUALS);
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.notEqual(root.get(criteria.getFieldName()), criteria.getValue());
    }
}
