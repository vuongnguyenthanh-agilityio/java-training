package com.agility.marketservice.mock;

import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.util.ProductStatusEnum;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class ProductMock {
  public static ProductDto getProduct() {
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
  public static List<ProductDto> getListProducts(int size) {
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
}
