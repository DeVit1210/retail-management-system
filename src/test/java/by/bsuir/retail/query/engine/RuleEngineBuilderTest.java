package by.bsuir.retail.query.engine;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.engine.impl.BasicRuleEngine;
import by.bsuir.retail.query.engine.impl.DateRuleEngine;
import by.bsuir.retail.query.engine.impl.NumberRuleEngine;
import by.bsuir.retail.query.engine.impl.TimeRuleEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RuleEngineBuilderTest {
    @Autowired
    private RuleEngineBuilder ruleEngineBuilder;
    @Autowired
    private SearchCriteriaMapper mapper;
    @Autowired
    private BeanFactory beanFactory;
    @Mock
    private SearchCriteriaDto criteriaDto;

    static Stream<Arguments> buildRuleEngineProvider() {
        return Stream.of(
                Arguments.of("fullName", (Predicate<RuleEngine>) ruleEngine -> ruleEngine instanceof BasicRuleEngine),
                Arguments.of("9.99", (Predicate<RuleEngine>) ruleEngine -> ruleEngine instanceof NumberRuleEngine),
                Arguments.of("2021-01-01", (Predicate<RuleEngine>) ruleEngine -> ruleEngine instanceof DateRuleEngine),
                Arguments.of("2021-01-01 18:00", (Predicate<RuleEngine>) ruleEngine -> ruleEngine instanceof TimeRuleEngine)
        );
    }

    @ParameterizedTest
    @MethodSource("buildRuleEngineProvider")
    void testBuildBasicRuleEngine(String value, Predicate<RuleEngine> predicate) {
        when(criteriaDto.getValue()).thenReturn(value);
        when(criteriaDto.getOperatorSignature()).thenReturn(Operator.EQUALS.getSignature());
        RuleEngine ruleEngine = ruleEngineBuilder.buildRuleEngine(criteriaDto);
        assertTrue(predicate.test(ruleEngine));
    }
}