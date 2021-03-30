package com.agility.currencyexchangeservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
  private String id;
  private String fullName;
  private String email;
  private RoleDto role;
}
