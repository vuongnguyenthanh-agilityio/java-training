package com.agility.marketservice.repository;

import com.agility.marketservice.model.Product;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IProductRepository extends MongoRepository<Product, String> {
  List<Product> findAllBy(TextCriteria criteria);
}
