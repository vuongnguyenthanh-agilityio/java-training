package com.agility.marketservice.controller;

import com.agility.marketservice.model.Category;
import com.agility.marketservice.repository.ICategoryRepository;
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
public class CategoryController {
  private final ICategoryRepository categoryRepository;

  @GetMapping("/api/categories")
  public ResponseEntity<List<Category>> getCategories() {
    List<Category> categories = categoryRepository.findAll();

    return new ResponseEntity<>(categories, HttpStatus.OK);
  }
}
