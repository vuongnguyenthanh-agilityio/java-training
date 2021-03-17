package com.agility.marketservice.mock;

import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.util.ProductStatusEnum;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class ProductMock {
  public static ProductDto getProductDto() {
    Faker faker = new Faker();
    Category category = CategoryMock.getCategory();
    ProductDto productDto = new ProductDto()
        .setCategory(category)
        .setName(faker.name().name())
        .setPrice(faker.number().numberBetween(0, 100000))
        .setStatus(ProductStatusEnum.PENDING)
        .setShippingServices(List.of(
            faker.number().randomNumber(10, true) + "",
            faker.number().randomNumber(10, true) + ""));

    return productDto;
  }

  public static Product getProduct() {
    Faker faker = new Faker();
    Category category = CategoryMock.getCategory();
    Product product = new Product()
        .setCategory(category)
        .setName(faker.name().name())
        .setPrice(faker.number().numberBetween(0, 100000))
        .setStatus(ProductStatusEnum.PENDING)
        .setShippingServices(List.of(
            faker.number().randomNumber(10, true) + "",
            faker.number().randomNumber(10, true) + ""));

    return product;
  }

  public static List<ProductDto> getListProductDto(int size) {
    Faker faker = new Faker();
    Category category = CategoryMock.getCategory();
    List<ProductDto> productDtoList = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      ProductDto productDto = new ProductDto()
          .setCategory(category)
          .setName(faker.name().name())
          .setPrice(faker.number().numberBetween(0, 100000))
          .setStatus(ProductStatusEnum.PENDING)
          .setShippingServices(List.of(
              faker.number().randomNumber(10, true) + "",
              faker.number().randomNumber(10, true) + ""));
      productDto.setId(faker.number().randomNumber(10, true) + "");
      productDtoList.add(productDto);
    }

    return productDtoList;
  }

  public static List<Product> getListProduct(int size,  Category category) {
    Faker faker = new Faker();
    List<Product> productList = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      Product product = new Product()
          .setCategory(category)
          .setName(faker.name().name())
          .setPrice(faker.number().numberBetween(0, 100000))
          .setStatus(ProductStatusEnum.APPROVED)
          .setShippingServices(List.of(
              faker.number().randomNumber(10, true) + "",
              faker.number().randomNumber(10, true) + ""));
      product.setId(faker.number().randomNumber(10, true) + "");
      productList.add(product);
    }

    return productList;
  }
}
