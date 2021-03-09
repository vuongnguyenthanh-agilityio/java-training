package com.agility.marketservice.repository;

import com.agility.marketservice.model.ShippingService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShippingServiceRepository extends MongoRepository<ShippingService, String> {
}
