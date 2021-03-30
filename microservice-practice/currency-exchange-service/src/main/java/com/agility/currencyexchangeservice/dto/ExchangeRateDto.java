package com.agility.currencyexchangeservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExchangeRateDto {
  private String id;
  private String from;
  private String to;
  private double rate;
}
