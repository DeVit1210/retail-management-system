package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.criteria.impl.DoubleSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.EqualsOperatorRule;
import by.bsuir.retail.query.rule.impl.NotEqualsOperatorRule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class NumberRuleEngineTest {
    @InjectMocks
    private NumberRuleEngine numberRuleEngine;
    @Mock
    private SearchCriteriaMapper mapper;
    @Mock
    private SearchCriteriaDto searchCriteriaDto;
    @Mock
    private DoubleSearchCriteria doubleSearchCriteria;

    static Stream<Arguments> doubleSearchCriteriaProvider() {
        return Stream.of(
                Arguments.of(
                        new DoubleSearchCriteria("purchaseCost", Operator.GREATER, 10),
                        GreaterNumberOperatorRule.class
                ),
                Arguments.of(
                        new DoubleSearchCriteria("purchaseCost", Operator.LOWER_OR_EQUALS, 10),
                        LowerEqualsNumberOperatorRule.class
                )
        );
    }

    static Stream<Arguments> basicSearchCriteriaProvider() {
        return Stream.of(
                Arguments.of(
                        new BasicSearchCriteria("purchaseCost", Operator.EQUALS, 10),
                        EqualsOperatorRule.class
                ),
                Arguments.of(
                        new BasicSearchCriteria("purchaseCost", Operator.NOT_EQUALS, 10),
                        NotEqualsOperatorRule.class
                )
        );
    }

    @ParameterizedTest
    @MethodSource("doubleSearchCriteriaProvider")
    void testProcessMethodWithDoubleSearchCriteria(DoubleSearchCriteria criteria, Class<?> expectedClass) {
        when(mapper.toNumberSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(criteria);
        numberRuleEngine.init(searchCriteriaDto);
        Rule rule = numberRuleEngine.process();
        assertEquals(rule.getClass(), expectedClass);
    }

    @ParameterizedTest
    @MethodSource("basicSearchCriteriaProvider")
    void testProcessMethodWithBasicSearchCriteria(BasicSearchCriteria criteria, Class<?> expectedClass) {
        when(mapper.toBasicSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(criteria);
        when(doubleSearchCriteria.getOperator()).thenReturn(Operator.EQUALS);
        when(mapper.toNumberSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(doubleSearchCriteria);
        numberRuleEngine.init(searchCriteriaDto);
        Rule rule = numberRuleEngine.process();
        assertEquals(rule.getClass(), expectedClass);
    }

}