package by.bsuir.retail.query.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SearchCriteria<T> {
    private String fieldName;
    private Operator operator;
    private T value;
}
