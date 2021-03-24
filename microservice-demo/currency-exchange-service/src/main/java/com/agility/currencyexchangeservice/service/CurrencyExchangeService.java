package com.agility.currencyexchangeservice.service;

import com.agility.currencyexchangeservice.controller.request.CurrencyExchangeReq;
import com.agility.currencyexchangeservice.model.CurrencyExchange;

public interface CurrencyExchangeService {
  CurrencyExchange create(CurrencyExchangeReq currencyExchange);
  CurrencyExchange get(String id);
}
