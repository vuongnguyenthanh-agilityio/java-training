package com.agility.currencyconversionservice.controller;

import com.agility.currencyconversionservice.dto.CurrencyConversionDto;
import com.agility.currencyconversionservice.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class CurrencyConversionController {
  private final CurrencyConversionService conversionService;
  @GetMapping("/currency-conversion/{quantity}/{from}-{to}")
  public ResponseEntity<CurrencyConversionDto> currencyConversion(
      @PathVariable("quantity") @NotNull double quantity,
      @PathVariable("from") @NotNull String from,
      @PathVariable("to") @NotNull String to
  ) {
    CurrencyConversionDto currencyConversionDto = conversionService.conversion(quantity, from, to);

    return new ResponseEntity<>(currencyConversionDto, HttpStatus.OK);
  }
}
