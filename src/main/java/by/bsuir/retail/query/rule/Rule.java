package by.bsuir.retail.query.rule;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface Rule {
    boolean evaluate();
    <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
