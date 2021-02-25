package com.agility.springbootexample.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IUserRepository extends MongoRepository<User, String> {
  List<User> findByNameContaining(String name);
  @Query("{'location.country': ?0}")
  List<User> findByCountry(String country);
  List<User> findByInterestCategories(String categoryId);
}
