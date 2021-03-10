package com.agility.marketservice.controller.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Created by Vuong Nguyen
 */
@Data
@Accessors(chain = true)
public class ProductRequest {
  @NotNull
  @Size(min = 2, message="Name should have at least 2 characters")
  private String name;
  @NotNull
  @NotEmpty
  private String categoryId;
  @DecimalMin(value = "0.0", inclusive = false)
  private double price;
  private String description;
  @NotEmpty
  private List<String> shippingServices;
}
