package com.agility.marketservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.*;
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
@Document(collection = "Products")
@CompoundIndexes({
    @CompoundIndex(name="category_status", def="{'category.id' : 1, 'status' : 1 }")
})
public class Product extends Entity {
  @TextIndexed
  private String name;
  @DBRef
  private Category category;
  private ProductStatus status;
  private double price;
  private String description;
  @DBRef
  private List<String> shippingServices;
  @DBRef
  private User createdBy;
}
