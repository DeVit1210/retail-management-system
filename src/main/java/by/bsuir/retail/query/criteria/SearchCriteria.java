package by.bsuir.retail.query.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SearchCriteria {
    private String fieldName;
    private Operator operator;
    public abstract <T> T getValue();
}
