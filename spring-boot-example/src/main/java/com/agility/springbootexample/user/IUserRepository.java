package com.agility.springbootexample.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IUserRepository extends MongoRepository<User, String> {
  List<User> findByNameContaining(String name);
}
