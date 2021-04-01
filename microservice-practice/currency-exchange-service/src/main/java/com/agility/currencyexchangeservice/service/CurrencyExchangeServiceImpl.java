package com.agility.currencyexchangeservice.service;

import com.agility.currencyexchangeservice.controller.request.CurrencyExchangeReq;
import com.agility.currencyexchangeservice.dto.ExchangeRateDto;
import com.agility.currencyexchangeservice.exception.CurrencyExchangeException;
import com.agility.currencyexchangeservice.model.CurrencyExchange;
import com.agility.currencyexchangeservice.repository.CurrencyExchangeRepository;
import com.agility.currencyexchangeservice.util.ExceptionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
  private final CurrencyExchangeRepository currencyExchangeRepository;

  /**
   * Handle create currency exchange
   *
   * @param currencyExchangeReq
   * @return
   */
  @Override
  public CurrencyExchange create(CurrencyExchangeReq currencyExchangeReq, String userId) {
    String name = currencyExchangeReq.getCurrencyFrom() + "-" + currencyExchangeReq.getCurrencyTo();
    // Check currency exchange already exist
    Optional<CurrencyExchange> currencyExchangeOptional = currencyExchangeRepository.findByName(name);
    if (currencyExchangeOptional.isPresent()) {
      throw CurrencyExchangeException.throwException(
          ExceptionTypeEnum.BAD_REQUEST,
          "The currency exchange already exist."
      );
    }

    CurrencyExchange currencyExchange = new CurrencyExchange()
        .setName(name.toUpperCase())
        .setDescription(currencyExchangeReq.getDescription())
        .setRate(currencyExchangeReq.getRate())
        .setCreatedById(userId);

    return currencyExchangeRepository.insert(currencyExchange);
  }

  /**
   * Handle get currency exchange by id
   * @param id
   * @return
   */
  @Override
  public CurrencyExchange get(String id) {
    Optional<CurrencyExchange> optionalCurrencyExchange = currencyExchangeRepository.findById(id);
    if (!optionalCurrencyExchange.isPresent()) {
      throw CurrencyExchangeException.throwException(
          ExceptionTypeEnum.NOT_FOUND,
          "The currency exchange id: " + id + " not found."
      );
    }

    return optionalCurrencyExchange.get();
  }

  @Override
  public ExchangeRateDto getExchangeRate(String from, String to) {
    Optional<CurrencyExchange> currencyExchangeOptional = currencyExchangeRepository.findByName(from + "-" + to);
    if (!currencyExchangeOptional.isPresent()) {
      throw CurrencyExchangeException.throwException(
          ExceptionTypeEnum.NOT_FOUND,
          "The currency exchange " + from + "-" + to + " not found."
      );
    }
    CurrencyExchange currencyExchange = currencyExchangeOptional.get();
    ExchangeRateDto exchangeRateDto = new ExchangeRateDto()
        .setId(currencyExchange.getId())
        .setFrom(from)
        .setTo(to)
        .setRate(currencyExchange.getRate());

    return exchangeRateDto;
  }

  @Override
  public List<CurrencyExchange> getList() {
    return currencyExchangeRepository.findAll();
  }
}
