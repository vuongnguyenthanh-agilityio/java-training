package com.agility.currencyconversionservice.exception;

import com.agility.currencyconversionservice.util.ExceptionTypeEnum;
import feign.Response;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeExceptionHandler implements FeignHttpExceptionHandler {
  @Override
  public Exception handle(Response response) {
    int status = response.status();
    return CurrencyConversionException.throwException(ExceptionTypeEnum.NOT_FOUND);
  }
}
