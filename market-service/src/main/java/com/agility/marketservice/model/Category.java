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
@Document(collection = "Categories")
public class Category extends Entity {
  private String name;
  private String description;
}
