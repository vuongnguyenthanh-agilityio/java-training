package com.agility.marketservice.repository;

import com.agility.marketservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRoleRepository extends MongoRepository<Role, String> {
}
