package by.bsuir.retail.query.engine.impl;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.criteria.Operator;
import by.bsuir.retail.query.criteria.impl.BasicSearchCriteria;
import by.bsuir.retail.query.rule.Rule;
import by.bsuir.retail.query.rule.impl.EqualsOperatorRule;
import by.bsuir.retail.query.rule.impl.NotEqualsOperatorRule;
import by.bsuir.retail.query.rule.impl.TrueOperatorRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BasicRuleEngineTest {
    @InjectMocks
    private BasicRuleEngine basicRuleEngine;
    @Mock
    private SearchCriteriaMapper mapper;
    @Mock
    private SearchCriteriaDto searchCriteriaDto;
    @Mock
    private BasicSearchCriteria basicSearchCriteria;

    @Test
    void testInitMethod() {
        when(mapper.toBasicSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(basicSearchCriteria);
        basicRuleEngine.init(searchCriteriaDto);
        verify(mapper, times(1)).toBasicSearchCriteria(searchCriteriaDto);
    }

    static Stream<Arguments> processTestProvider() {
        return Stream.of(
                Arguments.of(
                        new BasicSearchCriteria("fullName", Operator.EQUALS, "Mozol Denis"),
                        EqualsOperatorRule.class
                ),
                Arguments.of(
                        new BasicSearchCriteria("enabled", Operator.TRUE, null),
                        TrueOperatorRule.class
                ),
                Arguments.of(
                        new BasicSearchCriteria("fullName", Operator.NOT_EQUALS, "Mozol Denis"),
                        NotEqualsOperatorRule.class
                )
        );
    }

    @ParameterizedTest
    @MethodSource("processTestProvider")
    void testProcess(BasicSearchCriteria criteria, Class<?> expectedClass) {
        when(mapper.toBasicSearchCriteria(any(SearchCriteriaDto.class))).thenReturn(criteria);
        basicRuleEngine.init(searchCriteriaDto);
        Rule rule = basicRuleEngine.process();
        assertEquals(rule.getClass(), expectedClass);
    }
}