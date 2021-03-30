package com.agility.currencyconversionservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ExceptionResponse {
  private Date timestamp = new Date();
  private String message;
  private String details;
}
