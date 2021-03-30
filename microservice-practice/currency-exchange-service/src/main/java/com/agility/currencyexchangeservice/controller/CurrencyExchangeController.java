package com.agility.currencyexchangeservice.controller;

import com.agility.currencyexchangeservice.controller.request.CurrencyExchangeReq;
import com.agility.currencyexchangeservice.dto.ExchangeRateDto;
import com.agility.currencyexchangeservice.exception.CurrencyExchangeException;
import com.agility.currencyexchangeservice.model.CurrencyExchange;
import com.agility.currencyexchangeservice.service.CurrencyExchangeService;
import com.agility.currencyexchangeservice.util.ExceptionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class CurrencyExchangeController {
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  private final CurrencyExchangeService currencyExchangeService;

  @PostMapping("/currency-exchange")
  public ResponseEntity<CurrencyExchange> createCurrencyExchange(
      @RequestBody @Valid CurrencyExchangeReq currencyExchangeReq) {

    LOG.info("Currency exchange input -> {}", currencyExchangeReq);
    CurrencyExchange currencyExchange = currencyExchangeService.create(currencyExchangeReq);

    return new ResponseEntity<>(currencyExchange, HttpStatus.CREATED);
  }

  @GetMapping("/currency-exchange/{id}")
  public ResponseEntity<CurrencyExchange> getCurrencyExchange(@PathVariable("id") @NotNull String id) {
    LOG.info("Currency Id -> {}", id);
    CurrencyExchange currencyExchange = currencyExchangeService.get(id);

    return new ResponseEntity<>(currencyExchange, HttpStatus.OK);
  }

  @GetMapping("/currency-exchange/rates/{from}-{to}")
  public ResponseEntity<ExchangeRateDto> getCurrencyExchangeRate(
      @PathVariable("from") @NotNull String from,
      @PathVariable("to") @NotNull String to
  ) {
    LOG.info("Currency exchange from -> {} to -> {}", from, to);
    if (to == null || to.isEmpty()) {
      throw CurrencyExchangeException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Currency to is required.");
    }
    ExchangeRateDto exchangeRateDto = currencyExchangeService.getExchangeRate(from.toUpperCase(), to.toUpperCase());

    return new ResponseEntity<>(exchangeRateDto, HttpStatus.OK);
  }
}
