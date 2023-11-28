package by.bsuir.retail.query.rule.impl;

import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.SearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LowerOperatorRule extends Rule {
    public LowerOperatorRule(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public boolean evaluate() {
        return criteria.matches(Operator.LOWER);
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return criteria.handleLower(root, query, builder);
    }
}
