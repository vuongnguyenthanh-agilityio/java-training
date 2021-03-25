package com.agility.currencyexchangeservice.service;

import com.agility.currencyexchangeservice.controller.request.CurrencyExchangeReq;
import com.agility.currencyexchangeservice.exception.CurrencyExchangeException;
import com.agility.currencyexchangeservice.model.CurrencyExchange;
import com.agility.currencyexchangeservice.repository.CurrencyExchangeRepository;
import com.agility.currencyexchangeservice.util.ExceptionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
  public CurrencyExchange create(CurrencyExchangeReq currencyExchangeReq) {
    String name = currencyExchangeReq.getCurrencyFrom() + "-" + currencyExchangeReq.getCurrencyTo();
    // Check currency exchange already exist
    CurrencyExchange oldCurrencyExchange = currencyExchangeRepository.findByName(name);
    if (oldCurrencyExchange != null) {
      throw CurrencyExchangeException.throwException(
          ExceptionTypeEnum.BAD_REQUEST,
          "The currency exchange already exist."
      );
    }

    CurrencyExchange currencyExchange = new CurrencyExchange()
        .setName(name)
        .setDescription(currencyExchangeReq.getDescription())
        .setRate(currencyExchangeReq.getRate());

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
}
