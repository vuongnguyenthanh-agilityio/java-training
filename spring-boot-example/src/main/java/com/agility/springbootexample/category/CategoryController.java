package com.agility.springbootexample.category;

import com.agility.springbootexample.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private ICategoryRepository icategoryRepository;

  @GetMapping(path = "/categories")
  public ResponseEntity<List<Category>> getAll() {
    List<Category> categories = icategoryRepository.findAll();
    if (categories.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @PostMapping(path="/categories")
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    LOG.info(category.toString());
    Category newCategory = icategoryRepository.save(category);
    return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
  }

  @DeleteMapping(path="/categories")
  public ResponseEntity<Category> deleteCategory() {
    icategoryRepository.deleteAll();
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  public List<Category> getCategoryByIds(List<String > ids) {
      return icategoryRepository.findByIdIn(ids);
  }
}
