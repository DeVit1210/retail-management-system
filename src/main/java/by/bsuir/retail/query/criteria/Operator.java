package by.bsuir.retail.query.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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

    public static Operator fromSignature(String signature) {
        return Arrays.stream(values())
                .filter(operator -> operator.getSignature().equals(signature))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("illegal operator signature: " + signature));
    }
}
