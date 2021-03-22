package com.agility.marketservice.util;

import com.agility.marketservice.dto.FilterConditionDto;
import com.agility.marketservice.exception.MarketException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericFilterCriteriaBuilder {
  private static final Map<String, Function<FilterConditionDto, Criteria>>
      FILTER_CRITERIA = new HashMap<>();

  // Create map of filter
  static {
    FILTER_CRITERIA.put("EQUAL", condition -> Criteria.where(condition.getField()).is(condition.getValue()));
    FILTER_CRITERIA.put("NOT_EQUAL", condition -> Criteria.where(condition.getField()).ne(condition.getValue()));
    FILTER_CRITERIA.put("GREATER_THAN",
        condition -> Criteria
            .where(condition.getField())
            .gt(Integer.parseInt(condition.getValue())));
    FILTER_CRITERIA.put("GREATER_THAN_OR_EQUAL_TO",
        condition -> Criteria
            .where(condition.getField())
            .gte(Integer.parseInt(condition.getValue())));
    FILTER_CRITERIA.put("LESS_THAN",
        condition -> Criteria
            .where(condition.getField())
            .lt(Integer.parseInt(condition.getValue())));
    FILTER_CRITERIA.put("LESS_THAN_OR_EQUAL_TO",
        condition -> Criteria
            .where(condition.getField())
            .lte(Integer.parseInt(condition.getValue())));
    FILTER_CRITERIA.put("CONTAINS", condition -> Criteria.where(condition.getField()).regex((String) condition.getValue()));
    FILTER_CRITERIA.put("JOIN", condition ->  Criteria.where(condition.getField()).is(condition.getValue()));
  }

  /**
   * Build all the queries passed as parameters.
   *
   * @param andConditions
   * @param orConditions
   * @return
   */
  public Query addCondition(List<FilterConditionDto> andConditions, List<FilterConditionDto> orConditions) {
    List<FilterConditionDto> filterAndConditions = new ArrayList<>(andConditions);
    List<FilterConditionDto> filterOrConditions = new ArrayList<>(orConditions);

    List<Criteria> andCriteria = new ArrayList<>();
    List<Criteria> orCriteria = new ArrayList<>();

    // build criteria
    filterAndConditions.stream()
        .map(conditionDto -> andCriteria.add(buildCriteria(conditionDto)))
        .collect(Collectors.toList());
    filterOrConditions.stream()
        .map(conditionDto -> orCriteria.add(buildCriteria(conditionDto)))
        .collect(Collectors.toList());

    // Create query
    Criteria criteria = new Criteria();
    if (!andCriteria.isEmpty() && !orCriteria.isEmpty()) {
      return new Query(criteria.andOperator(andCriteria.toArray(new Criteria[0])).orOperator(orCriteria.toArray(new Criteria[0])));
    } else if (!andCriteria.isEmpty()) {
      return new Query(criteria.andOperator(andCriteria.toArray(new Criteria[0])));
    } else if (!orCriteria.isEmpty()) {
      return new Query(criteria.orOperator(orCriteria.toArray(new Criteria[0])));
    } else {
      return new Query();
    }
  }

  /**
   * Build the predicate according to the request
   *
   * @param conditionDto
   * @return
   */
  private Criteria buildCriteria(FilterConditionDto conditionDto) {
    Function<FilterConditionDto, Criteria> function = FILTER_CRITERIA.get(conditionDto.getFilterOperation().name());
    if (function == null) {
      throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Invalid filter condition.");
    }

    return function.apply(conditionDto);
  }
}
