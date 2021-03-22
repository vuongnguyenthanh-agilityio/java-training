package com.agility.marketservice.controller;

import com.agility.marketservice.model.ShippingService;
import com.agility.marketservice.repository.IShippingServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vuong Nguyen
 *
 * TODO: This class only gets data for testing. It doesn't include in the practice scope
 */
@RequiredArgsConstructor
@RestController
public class ShippingServiceController {
  private final IShippingServiceRepository shippingServiceRepository;

  @GetMapping("/api/shipping-services")
  public ResponseEntity<List<ShippingService>> getCategories() {
    List<ShippingService> shippingServices = shippingServiceRepository.findAll();

    return new ResponseEntity<>(shippingServices, HttpStatus.OK);
  }
}

