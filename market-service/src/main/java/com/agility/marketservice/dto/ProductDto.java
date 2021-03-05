package com.agility.marketservice.dto;

import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.ProductStatus;
import com.agility.marketservice.model.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created by Vuong Nguyen
 */

@Data
@Accessors(chain = true)
public class ProductDto {
  private String id;
  private String name;
  private Category category;
  private ProductStatus status;
  private double price;
  private String description;
  private List<String> shippingServices;
  private UserDto createdBy;
  private UserDto lastModifiedBy;
}
