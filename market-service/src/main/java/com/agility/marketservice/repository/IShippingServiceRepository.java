package com.agility.marketservice.repository;

import com.agility.marketservice.model.ShippingService;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IShippingServiceRepository extends MongoRepository<ShippingService, String> {
}
