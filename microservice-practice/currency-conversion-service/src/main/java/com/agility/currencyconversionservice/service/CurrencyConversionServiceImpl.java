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
    try {
      CurrencyConversionDto currencyConversionDto = proxy.getCurrencyExchangeRate(from, to);
      if (currencyConversionDto == null) {
        throw CurrencyConversionException.throwException(ExceptionTypeEnum.NOT_FOUND, "Not found");
      }
      double totalPrice = currencyConversionDto.getRate() * quantity;
      currencyConversionDto.setQuantity(quantity);
      currencyConversionDto.setTotalPrice(totalPrice);

      return currencyConversionDto;
    } catch (FeignException ex) {
      HttpStatus status = HttpStatus.resolve(ex.status());
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root;
      try {
        root = mapper.readTree(ex.contentUTF8());
      } catch (JsonProcessingException e) {
        throw  CurrencyConversionException.throwException(ExceptionTypeEnum.INTERNAL_ERROR, "Internal server error.");
      }
      ByteBuffer byteBuffer = ex.responseBody().get();
      String message = root.path("message").asText();
      if (ex.status() == 404) {
        throw CurrencyConversionException.throwException(ExceptionTypeEnum.NOT_FOUND, message);
      } else if (ex.status() == 400) {
        throw CurrencyConversionException.throwException(ExceptionTypeEnum.BAD_REQUEST, message);
      } else {
        throw  CurrencyConversionException.throwException(ExceptionTypeEnum.INTERNAL_ERROR, message);
      }
    }


  }
}
