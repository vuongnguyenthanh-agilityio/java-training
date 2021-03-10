package com.agility.marketservice.model;

import com.agility.marketservice.util.ProductStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
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
    @CompoundIndex(name="category_status", def="{'category.id' : 1, 'status' : 1 }"),
    @CompoundIndex(name="user_status", def="{'createdBy.id' : 1, 'status' : 1 }"),
    @CompoundIndex(name="shipping-service", def="{'shippingServices.id' : 1 }")
})
public class Product extends Entity {
  @TextIndexed
  private String name;
  @DBRef
  private Category category;
  private ProductStatusEnum status;
  private double price;
  private String description;
  private List<String> shippingServices;
  @DBRef
  @CreatedBy
  private User createdBy;
  @DBRef
  @LastModifiedBy
  private User lastModifiedBy;
}
