package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.*;

public class NotEqualsOperatorRule extends Rule {
    public NotEqualsOperatorRule(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public boolean evaluate() {
        return criteria.matches(Operator.NOT_EQUALS);
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return criteria.handleNotEquals(root, query, builder);
    }
}
