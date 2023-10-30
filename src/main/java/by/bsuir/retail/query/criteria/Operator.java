package by.bsuir.retail.query.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operator {
    EQUALS("="),
    NOT_EQUALS("!="),
    GREATER(">"),
    LOWER("<"),
    GREATER_OR_EQUALS(">="),
    LOWER_OR_EQUALS("<="),
    TRUE("+"),
    FALSE("-");

    private final String signature;
}
