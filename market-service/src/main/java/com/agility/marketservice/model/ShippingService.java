package com.agility.marketservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Vuong Nguyen
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "ShippingServices")
public class ShippingService extends Entity {
  private String name;
  private String description;
  @DBRef
  private List<String> productIds;
}
