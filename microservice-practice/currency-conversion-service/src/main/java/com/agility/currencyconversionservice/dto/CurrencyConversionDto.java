package com.agility.currencyconversionservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CurrencyConversionDto {
  private String from;
  private String to;
  private double rate;
  private double quantity;
  private double totalPrice;
}
