package by.bsuir.retail.mapper.query;

import by.bsuir.retail.query.criteria.impl.DoubleSearchCriteria;
import by.bsuir.retail.query.criteria.impl.IntegerSearchCriteria;
import by.bsuir.retail.dto.query.SearchCriteriaDto;
import by.bsuir.retail.query.criteria.impl.DateSearchCriteria;
import by.bsuir.retail.query.criteria.impl.TimeSearchCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper {
    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.operatorSignature))")
    @Mapping(target = "value", source = "value", dateFormat = "yyyy-MM-dd HH-mm-ss")
    TimeSearchCriteria toTimeSearchCriteria(SearchCriteriaDto dto);

    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.operatorSignature))")
    @Mapping(target = "value", source = "value", dateFormat = "yyyy-MM-dd")
    DateSearchCriteria toDateSearchCriteria(SearchCriteriaDto dto);

    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.operatorSignature))")
    @Mapping(target = "value", expression = "java(Integer.parseInt(dto.value))")
    IntegerSearchCriteria toNumberSearchCriteria(SearchCriteriaDto dto);

    @Mapping(target = "operator", expression = "java(Operator.fromSignature(dto.operatorSignature))")
    @Mapping(target = "value", expression = "java(Double.parseDouble(dto.value))")
    DoubleSearchCriteria toDoubleSearchCriteria(SearchCriteriaDto dto);
}
