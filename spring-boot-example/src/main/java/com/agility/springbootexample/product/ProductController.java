package com.agility.springbootexample.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
  @Autowired
  private ProductDaoService productDaoService;
  @Autowired
  private IProductValidation productValidation;

  @GetMapping(path="/products")
  public List<Product> getProducts() {
    List<Product> products = productDaoService.getProducts();

    return products;
  }

  @GetMapping(path="/products/{id}")
  public Product getProduct(@PathVariable int id) {
    Product product = productDaoService.getProduct(id);
    if (product == null) {
      throw new ProductNotFoundException("Not found Id: " + id);
    }

    return product;
  }

  @PostMapping(path="/products")
  public ResponseEntity<Object> createProduct(@RequestBody Product product) {
    productValidation.validate(product);
    Product newProduct = productDaoService.createProduct(product);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(newProduct.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }
}
