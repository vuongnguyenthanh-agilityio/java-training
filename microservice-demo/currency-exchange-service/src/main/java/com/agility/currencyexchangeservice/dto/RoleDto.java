package com.agility.currencyexchangeservice.dto;

import com.agility.currencyexchangeservice.util.UserRoleEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleDto {
  private String id;
  private UserRoleEnum code;
  private String name;
}
