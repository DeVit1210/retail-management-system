package by.bsuir.retail.query.criteria;


import by.bsuir.retail.mapper.query.SearchCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchCriteriaBuilder {
    private final SearchCriteriaMapper mapper;

    // TODO: implement ruleEngine
    // TODO: init rule engine based on dto.valueType
    //  (int -> get all subclasses of IntegerOperatorRule, build IntegerSearchCriteria and inject into rule engine all the rules)
    // TODO: create a cycle for all the search criteria passed and then build specification chain
}
