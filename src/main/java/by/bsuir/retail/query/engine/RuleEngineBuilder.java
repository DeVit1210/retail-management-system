package by.bsuir.retail.query.engine;

import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import by.bsuir.retail.query.engine.impl.BasicRuleEngine;
import by.bsuir.retail.query.engine.impl.DateRuleEngine;
import by.bsuir.retail.query.engine.impl.NumberRuleEngine;
import by.bsuir.retail.query.engine.impl.TimeRuleEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RuleEngineBuilder {
    private final SearchCriteriaMapper mapper;
    private final BeanFactory beanFactory;
    public RuleEngine buildRuleEngine(SearchCriteriaDto dto) {
        RuleEngine engine = switch (ValueTypeResolver.resolve(dto)) {
            case "date" -> beanFactory.getBean(DateRuleEngine.class, mapper);
            case "time" -> beanFactory.getBean(TimeRuleEngine.class, mapper);
            case "number" -> beanFactory.getBean(NumberRuleEngine.class, mapper);
            default -> beanFactory.getBean(BasicRuleEngine.class, mapper);
        };
        engine.init(dto);
        return engine;
    }

}
