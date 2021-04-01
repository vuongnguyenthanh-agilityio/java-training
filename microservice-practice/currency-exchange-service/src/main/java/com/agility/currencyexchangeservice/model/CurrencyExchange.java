package com.agility.currencyexchangeservice.model;

import com.agility.currencyexchangeservice.dto.UserDto;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Accessors(chain = true)
@Document(collection = "CurrencyExchange")
public class CurrencyExchange {
  private String id;
  @Indexed(unique = true, direction = IndexDirection.DESCENDING)
  private String name;
  private double rate;
  private String description;
  private String createdById;
  @CreatedDate
  private Date createdAt;
  @LastModifiedDate
  private Date updatedAt;
}
