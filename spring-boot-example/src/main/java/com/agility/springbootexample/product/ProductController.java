package com.agility.springbootexample.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

  @GetMapping(path="/products")
  public List<Product> getProducts() {
    ProductDaoService productDaoService = new ProductDaoService();
    List<Product> products = productDaoService.getProducts();

    return products;
  }
  @GetMapping(path="/products/{id}")
  public Product getProduct(@PathVariable int id) {
    ProductDaoService productDaoService = new ProductDaoService();
    Product product = productDaoService.getProduct(id);

    return product;
  }
}
