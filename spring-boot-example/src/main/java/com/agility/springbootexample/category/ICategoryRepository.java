package com.agility.springbootexample.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ICategoryRepository extends MongoRepository<Category, String> {
  List<Category> findByIdIn(List<String> ids);
}