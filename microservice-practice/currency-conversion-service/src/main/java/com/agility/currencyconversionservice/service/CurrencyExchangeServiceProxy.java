package com.agility.currencyconversionservice.service;

import com.agility.currencyconversionservice.dto.CurrencyConversionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
  @GetMapping("/v1/api/currency-exchange-rates/{from}-{to}")
  CurrencyConversionDto getCurrencyExchangeRate(
      @PathVariable("from") @NotNull String from,
      @PathVariable("to") @NotNull String to
  );
}
