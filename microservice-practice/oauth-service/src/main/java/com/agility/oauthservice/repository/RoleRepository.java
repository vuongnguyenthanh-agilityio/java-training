package com.agility.oauthservice.repository;

import com.agility.oauthservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
