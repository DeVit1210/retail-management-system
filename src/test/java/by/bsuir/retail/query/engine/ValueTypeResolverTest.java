package by.bsuir.retail.query.engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValueTypeResolverTest {
    static Stream<Arguments> numberTypeResolverProvider() {
        return Stream.of(
                Arguments.of("100-10", false),
                Arguments.of("100", true),
                Arguments.of("9.99", true)
        );
    }

    static Stream<Arguments> dateTypeResolverProvider() {
        return Stream.of(
                Arguments.of("2021-01-01", true),
                Arguments.of("2021-01-01 10:00", false)
        );
    }

    static Stream<Arguments> timeTypeResolverProvider() {
        return Stream.of(
                Arguments.of("2021-01-01", false),
                Arguments.of("2021-01-01 10:00", true)
        );
    }

    @ParameterizedTest
    @MethodSource("numberTypeResolverProvider")
    void testNumberTypeResolver(String value, boolean expected) {
        ValueTypeResolver resolver = new ValueTypeResolver.NumberTypeResolver();
        assertEquals(expected, resolver.matches(value));
    }

    @ParameterizedTest
    @MethodSource("dateTypeResolverProvider")
    void testDateTypeResolver(String value, boolean expected) {
        ValueTypeResolver resolver = new ValueTypeResolver.DateTypeResolver();
        assertEquals(expected, resolver.matches(value));
    }

    @ParameterizedTest
    @MethodSource("timeTypeResolverProvider")
    void testTimeTypeResolver(String value, boolean expected) {
        ValueTypeResolver resolver = new ValueTypeResolver.TimeTypeResolver();
        assertEquals(expected, resolver.matches(value));
    }
}