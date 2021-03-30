package com.agility.currencyconversionservice.service;

import com.agility.currencyconversionservice.dto.CurrencyConversionDto;

public interface CurrencyConversionService {
  CurrencyConversionDto conversion(double quantity, String from, String to);
}
