package com.agility.marketservice.dto;

import com.agility.marketservice.util.FilterOperation;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FilterConditionDto {
  private String field;
  private FilterOperation filterOperation;
  private String value;
}
