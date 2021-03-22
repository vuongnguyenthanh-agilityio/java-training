package com.agility.marketservice.service;

import com.agility.marketservice.dto.FilterConditionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilterBuilderService {
  Pageable getPageable(int size, int page, String order);
  List<FilterConditionDto> createFilterCondition(String criteria);
}
