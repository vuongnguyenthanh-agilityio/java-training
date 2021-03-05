package com.agility.marketservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Create by Vuong Nguyen
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class UserDto {
  private String id;
  private String fullName;
  private String email;
  private RoleDto role;
}
