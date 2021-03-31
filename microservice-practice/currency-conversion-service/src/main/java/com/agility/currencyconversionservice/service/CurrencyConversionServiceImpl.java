package com.agility.currencyconversionservice.service;

import com.agility.currencyconversionservice.dto.CurrencyConversionDto;
import com.agility.currencyconversionservice.exception.CurrencyConversionException;
import com.agility.currencyconversionservice.util.ExceptionTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService{
  private final CurrencyExchangeServiceProxy proxy;
  @Override
  public CurrencyConversionDto conversion(double quantity, String from, String to) {
    CurrencyConversionDto currencyConversionDto = proxy.getCurrencyExchangeRate(from, to);
    if (currencyConversionDto == null) {
      throw CurrencyConversionException.throwException(
          ExceptionTypeEnum.NOT_FOUND,
          "The currency exchange " + from + "-" + to + " not found."
      );
    }
    double totalPrice = currencyConversionDto.getRate() * quantity;
    currencyConversionDto.setQuantity(quantity);
    currencyConversionDto.setTotalPrice(totalPrice);

    return currencyConversionDto;
  }
}
