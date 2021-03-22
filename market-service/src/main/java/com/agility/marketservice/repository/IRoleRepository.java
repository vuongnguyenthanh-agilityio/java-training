package com.agility.marketservice.repository;

import com.agility.marketservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends MongoRepository<Role, String> {
}
