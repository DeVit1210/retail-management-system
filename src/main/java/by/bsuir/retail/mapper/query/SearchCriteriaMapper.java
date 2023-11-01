package by.bsuir.retail.mapper.query;

import by.bsuir.retail.query.criteria.impl.*;
import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.engine.RuleEngine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper {
    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.getOperatorSignature()))")
    @Mapping(target = "value", source = "value", dateFormat = "yyyy-MM-dd HH-mm-ss")
    TimeSearchCriteria toTimeSearchCriteria(SearchCriteriaDto dto);

    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.getOperatorSignature()))")
    @Mapping(target = "value", source = "value", dateFormat = "yyyy-MM-dd")
    DateSearchCriteria toDateSearchCriteria(SearchCriteriaDto dto);

    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.getOperatorSignature()))")
    @Mapping(target = "value", expression = "java(Double.parseDouble(dto.getValue()))")
    DoubleSearchCriteria toNumberSearchCriteria(SearchCriteriaDto dto);

    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.getOperatorSignature()))")
    BasicSearchCriteria toBasicSearchCriteria(SearchCriteriaDto dto);

}
