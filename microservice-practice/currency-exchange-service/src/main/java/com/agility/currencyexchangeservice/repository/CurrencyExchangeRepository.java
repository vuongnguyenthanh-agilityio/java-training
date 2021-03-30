package com.agility.currencyexchangeservice.repository;

import com.agility.currencyexchangeservice.model.CurrencyExchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyExchangeRepository extends MongoRepository<CurrencyExchange, String> {
  Optional<CurrencyExchange> findByName(String name);
}
