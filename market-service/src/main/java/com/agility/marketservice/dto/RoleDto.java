package com.agility.marketservice.dto;

import com.agility.marketservice.model.UserRoles;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleDto {
  private String id;
  private UserRoles code;
  private String name;
}
