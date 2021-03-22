package com.agility.marketservice.repository;

import com.agility.marketservice.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, String> {
}
