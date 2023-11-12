package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.criteria.impl.DateSearchCriteria;
import by.bsuir.retail.query.criteria.impl.DoubleSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class DateRuleEngineTest {
    @InjectMocks
    private DateRuleEngine dateRuleEngine;
    @Mock
    private SearchCriteriaMapper mapper;
    @Mock
    private SearchCriteriaDto searchCriteriaDto;
    @Mock
    private DateSearchCriteria dateSearchCriteria;

    static Stream<Arguments> dateSearchCriteriaProvider() {
        return Stream.of(
                Arguments.of(
                        new DateSearchCriteria("createdAt", Operator.LOWER, LocalDate.now()),
                        LowerDateOperatorRule.class
                ),
                Arguments.of(
                        new DateSearchCriteria("createdAt", Operator.GREATER_OR_EQUALS, LocalDate.now()),
                        GreaterEqualsDateOperatorRule.class
                )
        );
    }

    static Stream<Arguments> basicSearchCriteriaProvider() {
        return Stream.of(
                Arguments.of(
                        new BasicSearchCriteria("createdAt", Operator.NOT_EQUALS, LocalDate.now()),
                        NotEqualsOperatorRule.class
                ),
                Arguments.of(
                        new BasicSearchCriteria("createdAt", Operator.EQUALS, LocalDate.now()),
                        EqualsOperatorRule.class
                )
        );
    }

    @ParameterizedTest
    @MethodSource("dateSearchCriteriaProvider")
    void testProcessMethodWithDateSearchCriteria(DateSearchCriteria criteria, Class<?> expectedClass) {
        when(mapper.toDateSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(criteria);
        dateRuleEngine.init(searchCriteriaDto);
        Rule rule = dateRuleEngine.process();
        assertEquals(rule.getClass(), expectedClass);
    }

    @ParameterizedTest
    @MethodSource("basicSearchCriteriaProvider")
    void testProcessMethodWithBasicSearchCriteria(BasicSearchCriteria criteria, Class<?> expectedClass) {
        when(mapper.toBasicSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(criteria);
        when(dateSearchCriteria.getOperator()).thenReturn(Operator.EQUALS);
        when(mapper.toDateSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(dateSearchCriteria);
        dateRuleEngine.init(searchCriteriaDto);
        Rule rule = dateRuleEngine.process();
        assertEquals(rule.getClass(), expectedClass);
    }
}