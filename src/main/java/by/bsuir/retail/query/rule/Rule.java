package by.bsuir.retail.query.rule;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.criteria.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Rule {
    protected SearchCriteria criteria;
    public abstract boolean evaluate();
    public abstract <T> Predicate getResult(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
