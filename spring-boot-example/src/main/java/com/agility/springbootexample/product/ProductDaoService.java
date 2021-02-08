package com.agility.springbootexample.product;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDaoService {
  private static List<Product> products = new ArrayList<>();

  static {
    products.add(new Product(1,"Iphone 5", "A", 15));
    products.add(new Product(2,"Iphone 6", "7", 20));
  }

  protected List<Product> getProducts() {
    return this.products;
  }

  protected Product getProduct(int id) {
    Product product = products.stream()
        .filter(i -> i.getId() == id)
        .findAny()
        .orElse(null);

    System.out.println(product);
    return product;
  }

}
