package com.agility.marketservice.mock;

import com.agility.marketservice.model.Category;
import com.github.javafaker.Faker;

public class CategoryMock {
  public static Category getCategory() {
    Faker faker = new Faker();
    Category category = new Category()
        .setName(faker.name().name())
        .setDescription(faker.funnyName().name());

    return category;
  }
}
