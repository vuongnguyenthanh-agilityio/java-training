package com.agility.marketservice.dto;

import com.agility.marketservice.model.Category;
import com.agility.marketservice.util.ProductStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
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
  private ProductStatusEnum status;
  private double price;
  private String description;
  private List<String> shippingServices;
  private UserDto createdBy;
  private UserDto lastModifiedBy;
  private Date createdAt;
  private Date updatedAt;
}
