package com.agility.marketservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Vuong Nguyen
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "Roles")
public class Role extends Entity {
  private UserRoles code;
  private String name;
  private String description;
}
