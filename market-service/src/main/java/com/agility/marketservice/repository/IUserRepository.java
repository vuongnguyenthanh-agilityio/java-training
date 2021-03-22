package com.agility.marketservice.repository;

import com.agility.marketservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {
  User findByEmail(String email);
}
