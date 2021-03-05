package com.agility.marketservice.controller;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.dto.UserDto;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.model.User;
import com.agility.marketservice.repository.IProductRepository;
import com.agility.marketservice.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Vuong Nguyen
 */
@RestController
public class ProductController {
  @Autowired
  private IProductService iProductService;
  @Autowired
  private IProductRepository iProductRepository;

  @PostMapping("/api/products")
  private ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductRequest product) {
    ProductDto newProduct = iProductService.createProduct(product);

    return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
  }

  /**
   * TODO: Will update
   * @return
   */
  @GetMapping(path = "/api/products")
  public ResponseEntity<List<Product>> getAll() {
    List<Product> user = iProductRepository.findAll();

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
