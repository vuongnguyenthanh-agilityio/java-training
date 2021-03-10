package com.agility.marketservice.service;

import com.agility.marketservice.dto.FilterConditionDto;
import com.agility.marketservice.util.ExceptionTypeEnum;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.util.FilterOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilterBuilderService implements IFilterBuilderService{
  public static final int DEFAULT_PAGE = 1;
  public static final int DEFAULT_PAGE_SIZE = 10;
  public static final String SORT_ASC = "ASC";
  public static final String SORT_DESC = "DESC";
  private final String FILTER_CONDITION_DELIMITER = "\\|";
  private final String FILTER_SEARCH_DELIMITER = "&";

  /**
   * Get request pageable
   *
   * @param size
   * @param page
   * @param order
   * @return
   */
  @Override
  public Pageable getPageable(int size, int page, String order) {
    try {
      int pageSize = (size <= 0) ? DEFAULT_PAGE_SIZE : size;
      int currentPage = (page <= 0) ? 1 : page;

      if (order == null || order.isEmpty()) {
        return PageRequest.of(currentPage - 1, pageSize);
      }

      List<String> values = split(order, FILTER_CONDITION_DELIMITER);
      if (values.size() < 2) {
        throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Value order incorrect");
      }

      String key = values.get(0);
      String sortDirection = values.get(1);
      if (SORT_ASC.equalsIgnoreCase(sortDirection)) {
        return PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.ASC, key));
      } else if (SORT_DESC.equalsIgnoreCase(sortDirection)) {
        return  PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.DESC, key));
      } else {
        throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Value order incorrect. must be 'asc' or 'desc'");
      }
    } catch(Exception ex) {
      throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Create condition filter failed: " + ex.getMessage());
    }

  }

  /**
   * Create filter condition
   *
   * @param criteria
   * @return
   */
  @Override
  public List<FilterConditionDto> createFilterCondition(String criteria) {
    try {
      List<FilterConditionDto> filters = new ArrayList<>();
      if (criteria == null || criteria.isEmpty()) return filters;

      List<String> values = split(criteria, FILTER_SEARCH_DELIMITER);
      if (values.isEmpty()) return filters;

      values.forEach(v -> {
        List<String> childValues = split(v, FILTER_CONDITION_DELIMITER);
        FilterOperation filterOperation = FilterOperation.fromValue(childValues.get(1));
        if (filterOperation != null) {
          FilterConditionDto filterConditionDto = new FilterConditionDto()
              .setField(childValues.get(0))
              .setFilterOperation(filterOperation)
              .setValue(childValues.get(2));
          filters.add(filterConditionDto);
        }
      });

      return filters;
    } catch (Exception ex) {
      throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Create condition filter failed: " + ex.getMessage());
    }
  }

  /**
   * Handle split paths to get key and value
   *
   * @param paths
   * @param delimiter
   * @return
   */
  private static List<String> split(String paths, String delimiter) {
    return Stream.of(paths.split(delimiter))
        .collect(Collectors.toList());
  }
}
