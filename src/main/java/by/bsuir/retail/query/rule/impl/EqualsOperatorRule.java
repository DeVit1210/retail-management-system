package by.bsuir.retail.query.rule.impl;


import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EqualsOperatorRule implements Rule {
    private BasicSearchCriteria criteria;
    @Override
    public boolean evaluate() {
        return criteria.getOperator().equals(Operator.EQUALS);
    }

    @Override
    public <T> Predicate getResult(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(criteria.getFieldName()), criteria.getValue());
    }
}
