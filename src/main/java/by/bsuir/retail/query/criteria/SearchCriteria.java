package by.bsuir.retail.query.criteria;

import by.bsuir.retail.entity.RetailManagementEntity;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class SearchCriteria {
    protected String fieldName;
    private Operator operator;

    public <T> Predicate handleEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<Object> objectPath = root.get(this.fieldName);
        if(RetailManagementEntity.class.isAssignableFrom(objectPath.getJavaType())) {
            return builder.equal(objectPath.get("id"),getValue());
        }
        return builder.equal(objectPath, getValue());
    }

    public <T> Predicate handleNotEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.notEqual(root.get(this.fieldName), getValue());
    }

    public <T> Predicate handleIfTrue(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.isTrue(root.get(this.fieldName));
    }

    public <T> Predicate handleIfFalse(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.isFalse(root.get(this.fieldName));
    }

    public boolean matches(Operator operator) {
        return this.operator.equals(operator);
    }

    public abstract Object getValue();

    public abstract <T> Predicate handleGreater(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

    public abstract <T> Predicate handleGreaterEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

    public abstract <T> Predicate handleLower(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

    public abstract <T> Predicate handleLowerEquals(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
