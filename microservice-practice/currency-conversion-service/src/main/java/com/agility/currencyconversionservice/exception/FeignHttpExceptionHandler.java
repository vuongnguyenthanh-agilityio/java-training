package com.agility.currencyconversionservice.exception;

import feign.Response;

public interface FeignHttpExceptionHandler {
  Exception handle(Response response);
}
