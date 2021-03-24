package com.agility.currencyexchangeservice.controller.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Vuong Nguyen
 *
 * The Class define and validate data input from client request
 */
@Data
@Accessors(chain = true)
public class CurrencyExchangeReq {
  @NotNull
  @Size(min = 2, message="Currency from should have at least 2 characters")
  private String currencyFrom;
  @NotNull
  @Size(min = 2, message="Currency tom should have at least 2 characters")
  private String currencyTo;
  @DecimalMin(value = "0.0", inclusive = false)
  private double rate;
  private String description;
}
