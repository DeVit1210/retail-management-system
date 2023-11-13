package by.bsuir.retail.query.engine;

import by.bsuir.retail.dto.query.SearchCriteriaDto;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public interface ValueTypeResolver {
    String resolve();
    boolean matches(String value);
    static String resolve(SearchCriteriaDto searchCriteriaDto) {
        final String defaultResult = "default";
        if(searchCriteriaDto.getValue() == null) return defaultResult;
        return Stream.of(new DateTypeResolver(), new TimeTypeResolver(), new NumberTypeResolver())
                .filter(valueTypeResolver -> valueTypeResolver.matches(searchCriteriaDto.getValue()))
                .findFirst()
                .map(ValueTypeResolver::resolve)
                .orElse(defaultResult);
    }

    class DateTypeResolver implements ValueTypeResolver {
        @Override
        public String resolve() {
            return "date";
        }

        @Override
        public boolean matches(String value) {
            return Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(value).matches();
        }
    }

    class TimeTypeResolver implements ValueTypeResolver {
        @Override
        public String resolve() {
            return "time";
        }

        @Override
        public boolean matches(String value) {
             return Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}").matcher(value).matches();
        }
    }

    class NumberTypeResolver implements ValueTypeResolver {
        @Override
        public String resolve() {
            return "number";
        }

        @Override
        public boolean matches(String value) {
            return Pattern.compile("\\d+\\.?\\d*").matcher(value).matches();
        }
    }
}
