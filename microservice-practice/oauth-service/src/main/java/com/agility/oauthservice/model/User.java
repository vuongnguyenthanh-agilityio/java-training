package com.agility.oauthservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Vuong Nguyen
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "Users")
@ToString
public class User extends Entity {
  private String fullName;
  @Indexed(unique = true, direction = IndexDirection.DESCENDING)
  private String email;
  private String password;
  @DBRef
  private Role role;
}

